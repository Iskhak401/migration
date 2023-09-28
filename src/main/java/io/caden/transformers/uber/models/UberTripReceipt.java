package io.caden.transformers.uber.models;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import io.caden.transformers.uber.entities.UberTrip;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UberTripReceipt {

    private final Document document;
    private final String[] permittedTaxes;
    private final String[] benefits;

    private static final String MONEY_PATTERN = "(?:DOP|[$]|£|€|¥|₩|₹)\\s*\\d+(?:\\.\\d{2})?";

    public UberTripReceipt(final Document document, final String[] permittedTaxes, final String[] benefits) {
        this.document = document;
        this.permittedTaxes = permittedTaxes;
        this.benefits = benefits;
    }

    /**
     * Extract the receipt from uber and get the tax and the service fee
     *
     * @param uberTrip
     */
    public void setTaxAndServiceFee(UberTrip uberTrip) {
        Elements elements = document.select("body > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table:nth-child(4)");
        String htmlText = StringUtils.substringAfter(elements.text(), "Subtotal");

        if (htmlText.contains("••••")) {
            htmlText = StringUtils.substringBefore(htmlText, "••••");
        }

        Matcher m = Pattern.compile(MONEY_PATTERN).matcher(htmlText);
        if (m.find()) {
            htmlText = htmlText.replace(m.group(), "");
        }

        String numbers = htmlText.trim().replaceAll("[^\\d-.]", " ").trim();

        numbers = numbers.replace("-", "");
        numbers = numbers.trim().replace(" +", " ");

        List<String> taxesNumbers = new LinkedList<>(Arrays.asList(numbers.split(" ")));
        taxesNumbers.removeIf(tax -> !tax.contains("."));
        String[] taxesName =  htmlText.trim().split(MONEY_PATTERN);

        double taxSum = 0D;
        double serviceFee = 0D;
        if (taxesNumbers.size() == taxesName.length) {
            for (int i = 0; i < taxesName.length; i++) {
                taxesName[i] = taxesName[i].replaceAll("[-+.^:,]", "");
                taxesName[i] = StringUtils.normalizeSpace(taxesName[i].replace("\\n", ""));

                if (Arrays.stream(benefits).anyMatch(taxesName[i]::equalsIgnoreCase)) {
                    continue;
                }

                if (Arrays.stream(permittedTaxes).anyMatch(taxesName[i]::equalsIgnoreCase)) {
                    taxSum = taxSum + Double.parseDouble(taxesNumbers.get(i));
                    continue;
                }

                serviceFee = serviceFee + Double.parseDouble(taxesNumbers.get(i));
            }
        }

        uberTrip.setTax(round(taxSum));
        uberTrip.setServiceFee(round(serviceFee));
    }

    private double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
