package io.caden.transformers.uber.models;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.caden.transformers.uber.entities.PostalAddress;

public class PostalAddressExtraction {
    private PostalAddressExtraction() {
        throw new IllegalStateException("Utility class");
    }

    private static final String US_POSTAL_CODE_PATTERN = "\\d{5}(-\\d{4})?";

    public static PostalAddress getPostalAddress(String waypoint) {
        PostalAddress postalAddress = new PostalAddress();
        String[] data = waypoint.split(",");
        if (data.length > 2) {
            postalAddress.setAddressCountry(data[data.length - 1].trim());
            setUsPostalAddress(data, postalAddress);
            setNonUsPostalAddress(data, postalAddress);
        } else {
            postalAddress.setStreetAddress(data.length == 2 ? data[0] + " " + data[1].trim() : data[0]);
        }

        return postalAddress;
    }

    private static void setUsPostalAddress(String[] data, PostalAddress postalAddress) {
        if (postalAddress.getAddressCountry().contains("US")) {
            postalAddress.setAddressCountry("US");

            Matcher matcher = Pattern.compile(US_POSTAL_CODE_PATTERN).matcher(data[data.length - 2]);
            if (matcher.find()) {
                postalAddress.setPostalCode(matcher.group());
                postalAddress.setAddressRegion(data[data.length - 2].replaceAll(matcher.group(), "").trim());

                setLocalityAndStreetAddress(data, postalAddress);
            }
        }
    }

    private static void setNonUsPostalAddress(String[] data, PostalAddress postalAddress) {
        if (!postalAddress.getAddressCountry().contains("US")) {
            if (postalAddress.getAddressCountry().length() > 3) {
                postalAddress.setAddressCountry(convertCountryNameToIso(postalAddress.getAddressCountry()));
            }

            if (data[data.length - 2].matches(".*\\d.*")) {
                List<String> countriesPostalCodeWithSpaces = Arrays.asList("GB", "UK", "CZ", "GR", "GG", "IN", "LB", "SK", "SE");
                String[] cityAndPostalCode = data[data.length - 2].split("\\s+");
                if (countriesPostalCodeWithSpaces.stream()
                        .anyMatch(country -> country.equalsIgnoreCase(postalAddress.getAddressCountry()))) {
                    postalAddress.setPostalCode(cityAndPostalCode[cityAndPostalCode.length - 2] + " " +
                            cityAndPostalCode[cityAndPostalCode.length - 1]);
                    cityAndPostalCode = Arrays.copyOfRange(cityAndPostalCode, 0, cityAndPostalCode.length - 2);
                } else {
                    if (postalAddress.getAddressCountry() != null && postalAddress.getAddressCountry().equals("FR")) {
                        List<String> aCityAndPostalCode = new LinkedList<>(Arrays.asList(cityAndPostalCode));
                        aCityAndPostalCode.removeAll(Arrays.asList("", null));
                        postalAddress.setPostalCode(aCityAndPostalCode.get(0));
                        aCityAndPostalCode.remove(0);
                        cityAndPostalCode = aCityAndPostalCode.toArray(new String[0]);
                    } else {
                        postalAddress.setPostalCode(cityAndPostalCode[cityAndPostalCode.length - 1]);
                        cityAndPostalCode = Arrays.copyOf(cityAndPostalCode, cityAndPostalCode.length - 1);
                    }
                }
                postalAddress.setAddressRegion(String.join(" ", cityAndPostalCode).trim());
            } else {
                postalAddress.setAddressRegion(data[data.length - 2].trim());
            }

            setLocalityAndStreetAddress(data, postalAddress);
        }
    }

    private static void setLocalityAndStreetAddress(String[] data, PostalAddress postalAddress) {
        int length;
        if (data.length > 3) {
            postalAddress.setAddressLocality(data[data.length - 3].trim());
            length = data.length - 3;
        } else {
            length = data.length - 2;
        }

        StringBuilder streetAddress = new StringBuilder();
        for (int i = 0; i < length; i++) {
            streetAddress.append(" ").append(data[i]);
        }
        postalAddress.setStreetAddress(streetAddress.toString().trim());
    }

    private static String convertCountryNameToIso(String countryName) {
        countryName = translateCountryNameToEnglish(countryName);
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }

        return countries.get(countryName);
    }

    private static String translateCountryNameToEnglish(String countryName) {
        Locale outLocale = Locale.forLanguageTag("en");
        for (Locale l : Locale.getAvailableLocales()) {
            for (String languageIso: Locale.getISOLanguages()) {
                if (l.getDisplayCountry(Locale.forLanguageTag(languageIso)).equals(countryName)) {
                    return l.getDisplayCountry(outLocale);
                }
            }
        }

        return countryName;
    }
}
