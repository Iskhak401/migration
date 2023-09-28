package io.caden.transformers.shared.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;

public final class RDFUtil {
  private RDFUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static Date getDate(String date) {
    List<String> formatStrings = Arrays.asList(
      "yyyy-MM-dd'T'HH:mm:ss",
      "yyyy-MM-dd'T'HH:mm:ss.SSS",
      "yyyy-MM-dd'T'HH:mm:ssXXX",
      "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
      "E MMM dd yyyy HH:mm:ss 'GMT'z",
      "yyyy-MM-dd'T'HH:mm"
    );

    for (String dateString: formatStrings) {
      DateFormat dateFormat = new SimpleDateFormat(dateString);
      try {
        return dateFormat.parse(
          date.replaceAll("[+-]\\d\\d:\\d\\d", "").replace("Z", "")
        );
      } catch (Exception e) {
        // TODO: handle exception
      }
    }

    return null;
  }

  public static Integer getInteger(final String value) {
    try {
      return Integer.parseInt(value);
    } catch (Exception e) {
      return null;
    }
  }

  public static Double getDouble(final String value) {
    try {
      return Double.parseDouble(value);
    } catch (Exception e) {
      return null;
    }
  }

  public static String getValue(final BindingSet bindingSet, final String varName) {
    Value value = bindingSet.getValue(varName);

    if (value != null) {
      return value.stringValue();
    }

    return null;
  }

  public static <T extends Enum<T>> T getEnum(final Class<T> enumType, final String value) {
    try {
      return Enum.valueOf(enumType, value);
    } catch (Exception e) {
      return null;
    }
  }
}
