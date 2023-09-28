package io.caden.transformers.airbnb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Util class for the Airbnb intake that parses dates from strings
public class AirbnbDateUtil {
    private static final String DATE_PATTERN = "MMM d, yyyy";

    private AirbnbDateUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static List<Date> parseDateRange(String text) throws ParseException {
        if (text.length() <= 17) {
            return parseSameMonthDateRange(text);
        } else if (text.length() <= 21) {
            return parseDifferentMonthDateRange(text);
        } else {
            return parseDifferentYearDateRange(text);
        }
    }

    /**
     * Parses text in the same month, Sample: Oct 26–27, 2011
     *
     * @param source A <code>String</code> to be parsed.
     * @return A <code>List</code> containing the start and end date parsed from the
     *         string.
     * @exception ParseException if the specified string cannot be parsed.
     */
    private static List<Date> parseSameMonthDateRange(final String source) throws ParseException {
        String checkIn = source.replaceAll("\\s*–.+,", ",");
        String checkout = source.replaceAll("\\d+\\s*–\\s*", "");

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);

        return Arrays.asList(
                dateFormat.parse(checkIn),
                dateFormat.parse(checkout)
        );
    }

    /**
     * Parses text with different months, Sample: May 19 – Jun 22, 2019
     *
     * @param source A <code>String</code> to be parsed.
     * @return A <code>List</code> containing the start and end date parsed from the
     *         string.
     * @exception ParseException if the specified string cannot be parsed.
     */
    private static List<Date> parseDifferentMonthDateRange(final String source) throws ParseException {
        String checkIn = source.replaceAll("\\s*–.+,", ",");
        String checkout = source.split("–")[1].trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);

        return Arrays.asList(
                dateFormat.parse(checkIn),
                dateFormat.parse(checkout)
        );
    }

    /**
     * Parses text with different years, Sample: Dec 11 - Jan 26, 2022/2023
     *
     * @param source A <code>String</code> to be parsed.
     * @return A <code>List</code> containing the start and end date parsed from the
     *         string.
     * @exception ParseException if the specified string cannot be parsed.
     */
    private static List<Date> parseDifferentYearDateRange(final String source) throws ParseException {
        String[] tokens = source.split(",");
        String dates = tokens[0];
        String years = tokens[1];

        String[] dateTokens = dates.split("–");
        String[] yearTokens = years.split("/");

        String checkIn = dateTokens[0] + ", " + yearTokens[0];
        String checkout = dateTokens[1] + ", " + yearTokens[1];

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);

        return Arrays.asList(
                dateFormat.parse(checkIn),
                dateFormat.parse(checkout)
        );
    }
}
