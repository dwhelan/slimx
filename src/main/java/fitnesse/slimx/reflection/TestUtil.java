package fitnesse.slimx.reflection;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class TestUtil {

  public static void assertTable(List<List<String>> actual, List<String>... expected) {
    assertTable("", actual, expected);
  }

  public static void assertTable(String prefix, List<List<String>> actual, List<String>... expected) {
    assertEquals(expected.length, actual.size());

    for (int row = 0; row < actual.size(); row++)
      assertRow(prefix, actual.get(row), expected[row]);
  }

  public static void assertRow(List<String> actual, String... expected) {
    assertRow("", actual, expected);
  }

  public static void assertRow(String prefix, List<String> actual, String... expected) {
    assertRow(prefix, actual, Arrays.asList(expected));
  }

  public static void assertRow(String prefix, List<String> actual, List<String> expected) {
    StringBuilder expectedString = new StringBuilder();
    for (String cell : expected)
      expectedString.append(prefix + cell).append(", ");

    StringBuilder actualString = new StringBuilder();
    for (String cell : actual)
      actualString.append(cell).append(", ");

    assertEquals(expectedString.toString(), actualString.toString());
  }
}
