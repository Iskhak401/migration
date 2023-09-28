package io.caden.transformers.amazon.models;

import io.caden.transformers.amazon.entities.*;
import io.caden.transformers.shared.dtos.DataSchemaChangeDto;
import io.caden.transformers.shared.exceptions.DataSchemaException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
public class AmazonOrderDetailsPage {

  private final Document document;

  public AmazonOrderDetailsPage(final Document document) {
    this.document = document;
  }

  public String getOrderHtml() {
    Element orderDetails = this.document.selectFirst("#orderDetails");

    if (orderDetails != null) {
      orderDetails.child(0).remove();
      orderDetails.child(orderDetails.children().size() - 1).remove();

      return orderDetails.outerHtml();
    }

    return "";
  }

  public List<AmazonOrder> getAmazonOrders(String amazonUrl) throws DataSchemaException {
    try {
      List<AmazonOrder> amazonOrders = new ArrayList<>();

      for (Element orderDetails : this.document.select("#orderDetails")) {
        AmazonOrder amazonOrder = new AmazonOrder();

        String orderDate = orderDetails.select(".order-date-invoice-item").get(0).text().replace("Ordered on", "").trim();
        String orderNumber = orderDetails.select(".order-date-invoice-item").get(1).text().replace("Order#", "").trim();

        String shippingAddress = String.join(
          ", ",
          orderDetails.select(".displayAddressLI").stream().skip(1).map(
            li -> StringEscapeUtils.unescapeHtml4(li.text()).trim()
          ).collect(Collectors.toList())
        );

        String orderStatus = "DELIVERED";

        Element shipmentInfoContainer = orderDetails.selectFirst(".js-shipment-info-container");

        if (shipmentInfoContainer != null) {
           orderStatus = shipmentInfoContainer.select(".a-row").get(1).text().split(" ")[0];
        }

        String paymentIdLastFour = null;
        try {
          paymentIdLastFour = orderDetails.selectFirst(".a-row .a-spacing-mini").text().replaceAll("[^\\d.]", "").trim();
        } catch (Exception e) {
          log.error("Error parsing paymentIdLastFour.", e);
        }

        String deliveryFee = "0";
        String pricingTags = "div.a-fixed-right-grid-col.a-col-right";

        if (!orderDetails.select("#od-subtotals").isEmpty()) {
          pricingTags = "#od-subtotals";

          Optional<Element> deliveryEl = orderDetails.selectFirst(pricingTags).children().stream()
                  .filter(element -> element.text().toLowerCase().contains("shipping"))
            .findFirst();

          if (deliveryEl.isPresent()) {
            deliveryFee = parseNumber(deliveryEl.get().child(1).text());
          } else {
            log.error("Unable to find order delivery fee element");
          }
        }

        Optional<Element> taxEl = orderDetails.selectFirst(pricingTags).children().stream()
                .filter(element -> element.text().toLowerCase().contains("estimated"))
                .findFirst();

        if (taxEl.isPresent()) {
          String tax = parseNumber(taxEl.get().child(1).text());
          amazonOrder.setTax(Double.parseDouble(tax));
        } else {
          log.error("Unable to find order tax element");
        }

        Optional<Element> priceEl = orderDetails.selectFirst(pricingTags).children().stream()
          .filter(element -> element.text().toLowerCase().contains("subtotal"))
          .findFirst();

        if (priceEl.isPresent()) {
          String price = parseNumber(priceEl.get().child(1).text());
          amazonOrder.setPrice(Double.parseDouble(price));
        } else {
          log.error("Unable to find order price element");
        }

        amazonOrder.setOrderDate(new SimpleDateFormat("MMMM dd, yyyy").parse(orderDate));
        amazonOrder.setOrderNumber(orderNumber);
        amazonOrder.setShippingAddress(shippingAddress);
        //TODO: this should come from invoice
        amazonOrder.setBillingAddress(shippingAddress);
        amazonOrder.setPriceCurrency("USD");
        amazonOrder.setPaymentIdLastFour(paymentIdLastFour);
        amazonOrder.setOrderStatus(mapOrderStatus(orderStatus));
        amazonOrder.setDeliveryFee(Double.parseDouble(deliveryFee));
        amazonOrder.setAmazonOrderItems(new HashSet<>());

        this.parseOrderItems(orderDetails, amazonOrder, shippingAddress, amazonUrl);

        amazonOrders.add(amazonOrder);
      }

      return amazonOrders;
    } catch (Exception e) {
      throw new DataSchemaException(DataSchemaChangeDto.fromHtml(this.document.outerHtml()), e);
    }
  }

  // Takes in orderDetails HTML element and parses each order item
  private void parseOrderItems(Element orderDetails, AmazonOrder order, String shippingAddress, String amazonUrl) {
    for (Element shipment : orderDetails.select(".shipment")) {
      for(Element card: shipment.select(".a-fixed-left-grid")) {
        AmazonOrderItem amazonOrderItem = new AmazonOrderItem();

        Element item = card.child(0).child(1);
        Element qty = card.child(0).child(0).child(0).selectFirst(".item-view-qty");
        Element name = item.child(0).child(0);

        Element priceEl = item.selectFirst(".a-color-price");

        if (priceEl != null) {
          String itemPrice = priceEl.text().replaceAll("[^0-9\\.]", "");

          try {
            amazonOrderItem.setPrice(Double.parseDouble(itemPrice));
          } catch (Exception e) {
            log.error("Cannot parse to double order item price: " + itemPrice, e);
          }
        } else {
          log.error("Unable to find order item price element");
        }

        amazonOrderItem.setName(name.text().trim());
        amazonOrderItem.setLink(name.absUrl("href"));
        amazonOrderItem.setReturned(shipment.text().toLowerCase().contains("refunded"));
        amazonOrderItem.setQuantity(qty == null ? 1 : Integer.valueOf(qty.text().trim()));
        amazonOrderItem.setPriceCurrency("USD");
        amazonOrderItem.setUuid(UUID.randomUUID().toString());

        AmazonParcelDelivery parcelDelivery = new AmazonParcelDelivery();
        parcelDelivery.setTrackingNumber(name.attr("elementTrackingId").isEmpty() ? null : name.attr("elementTrackingId"));
        parcelDelivery.setDeliveryMethod(DeliveryMethod.PARCEL_SERVICE);
        parcelDelivery.setDeliveryAddress(shippingAddress);

        Element amazonSellerElement = card.selectFirst(".a-size-small.a-color-secondary");

        if (amazonSellerElement != null) {
          String soldBy = amazonSellerElement.text().split(" ")[2].trim();
          String identifier = "";

          if (!amazonSellerElement.children().isEmpty()) {
            Optional<String> idOption = Arrays.stream(amazonSellerElement.child(0).attr("href").split("&"))
              .filter(x -> x.contains("seller=")).findFirst();

            if (idOption.isPresent()) {
              identifier = idOption.get().replace("seller=", "");
            }
          }

          if (identifier.isBlank()) {
            // TODO: unable to find id, so we assume seller is Amazon.com
            identifier = amazonUrl;
          }

          AmazonSeller amazonSeller = new AmazonSeller();
          amazonSeller.setName(soldBy);
          amazonSeller.setIdentifier(identifier);
          amazonSeller.setSameAs(String.format("%s/sp?seller=%s", amazonUrl, identifier));

          amazonOrderItem.setSeller(amazonSeller);
        }

        amazonOrderItem.setParcelDelivery(parcelDelivery);
        order.getAmazonOrderItems().add(amazonOrderItem);
      }
    }
  }
  private AmazonOrderStatus mapOrderStatus(String orderStatus) {
    orderStatus = orderStatus.toUpperCase();
    if(orderStatus.contains("DELIVERED"))
      return AmazonOrderStatus.ORDER_DELIVERED;
    else if(orderStatus.contains("CANCELLED"))
      return AmazonOrderStatus.ORDER_CANCELLED;
    else if(orderStatus.contains("TRANSIT") || orderStatus.contains("ARRIVING"))
      return AmazonOrderStatus.ORDER_IN_TRANSIT;
    else if(orderStatus.contains("PAYMENT"))
      return AmazonOrderStatus.ORDER_PAYMENT_DUE;
    else if(orderStatus.contains("PICKUP"))
      return AmazonOrderStatus.ORDER_PICKUP_AVAILABLE;
    else if(orderStatus.contains("PROBLEM"))
      return AmazonOrderStatus.ORDER_PROBLEM;
    else if(orderStatus.contains("PROCESSING"))
      return AmazonOrderStatus.ORDER_PROCESSING;
    else if(orderStatus.contains("RETURN"))
      return AmazonOrderStatus.ORDER_RETURNED;
    else if (orderStatus.contains("REFUNDED"))
      return AmazonOrderStatus.ORDER_REFUNDED;
    else if (orderStatus.contains("EXCHANGED"))
      return AmazonOrderStatus.ORDER_EXCHANGED;

    return null;
  }

  private String parseNumber(String str) {
    return str.replaceAll("[^0-9\\.]", "");
  }
}
