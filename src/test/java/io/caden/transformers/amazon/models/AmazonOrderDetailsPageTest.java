package io.caden.transformers.amazon.models;

import io.caden.transformers.amazon.entities.AmazonOrder;
import io.caden.transformers.shared.exceptions.DataSchemaException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AmazonOrderDetailsPageTest {

    @InjectMocks
    AmazonOrderDetailsPage amazonOrderDetailsPage;

    private Document document;

    @BeforeEach
    void setUp() {
        try {
            InputStream inputStream = AmazonOrderDetailsPageTest.class.getClassLoader().getResourceAsStream("amazon/amazon-order-details.html");

            document = Jsoup.parse(inputStream, null, "");

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getOrderHtmlWithBlankHTMLTest() {
        Document doc = new Document("");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(doc);
        Assert.assertEquals("", amazonOrderDetailsPage.getOrderHtml());
    }

    @Test
    void getOrderHtmlTest() {
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        Assert.assertNotNull(amazonOrderDetailsPage.getOrderHtml());
    }

    @Test
    void getOrdersTest() {
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
    }

    @Test
    void getOrdersWithMissingInfoTest() {
        Element orderDetail = document.select("#orderDetails").first();
        String pricingTags = "div.a-fixed-right-grid-col.a-col-right";

        Optional<Element> deliveryEl = orderDetail.selectFirst(pricingTags).children().stream()
                .filter(element -> element.text().toLowerCase().contains("shipping"))
                .findFirst();;
        Element elementToRemove = deliveryEl.get().child(1);
        elementToRemove.text("");

        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        Exception exception = Assert.assertThrows(DataSchemaException.class, () -> amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("class io.caden.transformers.shared.exceptions.DataSchemaException", exception.getClass().toString());
    }

    @Test
    void setOrderStatusToCancelledTest() {
        Element element = document.select("#orderDetails").select(".js-shipment-info-container")
                .select(".a-row").get(1);

        element.text("CANCELLED Jun 10, 2023");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        AmazonOrder amazonOrder = (amazonOrderDetailsPage.getAmazonOrders("amazon.com")).get(0);

        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("ORDER_CANCELLED",amazonOrder.getOrderStatus().toString());
    }

    @Test
    void setOrderStatusToTransitTest() {
        Element element = document.select("#orderDetails").select(".js-shipment-info-container")
                .select(".a-row").get(1);

        element.text("TRANSIT Jun 10, 2023");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        AmazonOrder amazonOrder = (amazonOrderDetailsPage.getAmazonOrders("amazon.com")).get(0);

        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("ORDER_IN_TRANSIT",amazonOrder.getOrderStatus().toString());
    }

    @Test
    void setOrderStatusToPaymentTest() {
        Element element = document.select("#orderDetails").select(".js-shipment-info-container")
                .select(".a-row").get(1);

        element.text("PAYMENT Jun 10, 2023");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        AmazonOrder amazonOrder = (amazonOrderDetailsPage.getAmazonOrders("amazon.com")).get(0);

        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("ORDER_PAYMENT_DUE",amazonOrder.getOrderStatus().toString());
    }

    @Test
    void setOrderStatusToPickupTest() {
        Element element = document.select("#orderDetails").select(".js-shipment-info-container")
                .select(".a-row").get(1);

        element.text("PICKUP Jun 10, 2023");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        AmazonOrder amazonOrder = (amazonOrderDetailsPage.getAmazonOrders("amazon.com")).get(0);

        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("ORDER_PICKUP_AVAILABLE",amazonOrder.getOrderStatus().toString());
    }

    @Test
    void setOrderStatusToProblemTest() {
        Element element = document.select("#orderDetails").select(".js-shipment-info-container")
                .select(".a-row").get(1);

        element.text("PROBLEM Jun 10, 2023");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        AmazonOrder amazonOrder = (amazonOrderDetailsPage.getAmazonOrders("amazon.com")).get(0);

        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("ORDER_PROBLEM",amazonOrder.getOrderStatus().toString());
    }

    @Test
    void setOrderStatusToProcessingTest() {
        Element element = document.select("#orderDetails").select(".js-shipment-info-container")
                .select(".a-row").get(1);

        element.text("PROCESSING Jun 10, 2023");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        AmazonOrder amazonOrder = (amazonOrderDetailsPage.getAmazonOrders("amazon.com")).get(0);

        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("ORDER_PROCESSING" ,amazonOrder.getOrderStatus().toString());
    }

    @Test
    void setOrderStatusToReturnedTest() {
        Element element = document.select("#orderDetails").select(".js-shipment-info-container")
                .select(".a-row").get(1);

        element.text("RETURN Complete");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        AmazonOrder amazonOrder = (amazonOrderDetailsPage.getAmazonOrders("amazon.com")).get(0);

        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("ORDER_RETURNED",amazonOrder.getOrderStatus().toString());
    }

    @Test
    void setOrderStatusToRefundedTest() {
        Element element = document.select("#orderDetails").select(".js-shipment-info-container")
                .select(".a-row").get(1);

        element.text("REFUNDED Jun 10, 2023");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        AmazonOrder amazonOrder = (amazonOrderDetailsPage.getAmazonOrders("amazon.com")).get(0);

        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("ORDER_REFUNDED",amazonOrder.getOrderStatus().toString());
    }

    @Test
    void setOrderStatusToExchangedTest() {
        Element element = document.select("#orderDetails").select(".js-shipment-info-container")
                .select(".a-row").get(1);

        element.text("EXCHANGED Jun 10, 2023");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        AmazonOrder amazonOrder = (amazonOrderDetailsPage.getAmazonOrders("amazon.com")).get(0);

        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("ORDER_EXCHANGED",amazonOrder.getOrderStatus().toString());
    }

    @Test
    void setOrderStatusToArrivingTest() {
        Element element = document.select("#orderDetails").select(".js-shipment-info-container")
                .select(".a-row").get(1);

        element.text("ARRIVING Jun 10, 2023");
        amazonOrderDetailsPage = new AmazonOrderDetailsPage(document);
        AmazonOrder amazonOrder = (amazonOrderDetailsPage.getAmazonOrders("amazon.com")).get(0);

        Assert.assertNotNull(amazonOrderDetailsPage.getAmazonOrders("amazon.com"));
        Assert.assertEquals("ORDER_IN_TRANSIT",amazonOrder.getOrderStatus().toString());
    }
}

