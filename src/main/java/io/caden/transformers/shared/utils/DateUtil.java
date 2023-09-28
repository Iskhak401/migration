package io.caden.transformers.shared.utils;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
  private DateUtil() {
    throw new IllegalStateException("Utility class");
  }
  public static Date getDateWithoutTime(final Date date) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      return formatter.parse(formatter.format(date));
    } catch (Exception ex) {
      return date;
    }
  }

  public static boolean compareToInclusive(final Date dateToCompare, final Date startDate, final Date endDate) {
    return startDate.compareTo(dateToCompare) * dateToCompare.compareTo(endDate) >= 0;
  }

  public static boolean compareToExclusive(final Date dateToCompare, final Date startDate, final Date endDate) {
    return startDate.compareTo(dateToCompare) * dateToCompare.compareTo(endDate) > 0;
  }

  public static String monthToString(Integer month) {
    return Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.US);
  }

  public static String getDayName(Integer dayNumber) {
    switch (dayNumber) {
      case 0:
        return "Monday";
      case 1:
        return "Tuesday";
      case 2:
        return "Wednesday";
      case 3:
        return "Thursday";
      case 4:
        return "Friday";
      case 5:
        return "Saturday";
      case 6:
        return "Sunday";
      default:
        return null;
    }
  }
}
