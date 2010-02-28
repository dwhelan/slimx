package fitnesse.slimx.reflection;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class TestUtil {

    public static void assertRow(List<String> row, String... expectedCells) {
        assertRow("", row, expectedCells);
    }

    public static void assertRow(String prefix, List<String> row, String... expectedCells) {

        StringBuilder expected = new StringBuilder();
        for (String cell : expectedCells)
            expected.append(prefix + cell).append(", ");

        StringBuilder actual = new StringBuilder();
        for (String cell : row)
            actual.append(cell).append(", ");

        assertEquals(expected.toString(), actual.toString());
    }
}
