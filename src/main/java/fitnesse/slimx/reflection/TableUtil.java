package fitnesse.slimx.reflection;

import java.util.ArrayList;
import java.util.List;

import fitnesse.slim.SlimExtensions;

@SuppressWarnings("unchecked")
public class TableUtil {

  public static String annotate(String annotation, Object cell, Object... messages) {
    StringBuilder contents = new StringBuilder(annotation);
    contents.append(":").append(SlimExtensions.convertToString(cell));

    for (Object message : messages)
      contents.append(' ').append(message);

    return contents.toString();
  }

  public static String pass(Object cell, Object... messages) {
    return annotate("pass", cell, messages);
  }

  public static String fail(Object cell, Object... messages) {
    return annotate("fail", cell, messages);
  }

  public static String error(Object cell, Object... messages) {
    return annotate("error", cell, messages);
  }

  public static String report(Object cell, Object... messages) {
    if (cell instanceof ErrorValue)
      return error(cell, messages);
    return annotate("report", cell, messages);
  }

  public static List<String> reportRow(List row) {
    List<String> result = new ArrayList<String>();

    for (Object cell : row)
      result.add(report(cell));

    return result;
  }

  public static List<List<String>> reportTable(List table) {
    List<List<String>> result = new ArrayList<List<String>>();

    for (Object row : table)
      result.add(reportRow((List) row));

    return result;
  }
}