package fitnesse.slimx.fixtures;

import static fitnesse.slimx.reflection.TestUtil.assertRow;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import fitnesse.slimx.reflection.Property;
import fitnesse.slimx.reflection.examples.Sample;

public class ShowVariableTest {

    private static final Object sample = new Sample("value1", 1.0);
    private static final Object sampleA = new Sample("value1A", 2.0);
    private static final List<Object> samples = Arrays.asList(sample, sampleA);

    private final ArrayList<List<String>> table = new ArrayList<List<String>>();

    @Test
    public void should_return_all_properties() {
        ShowVariable showVariable = new ShowVariable(sample);

        List<List<String>> result = showVariable.doTable(table);

        assertRow("report:", result.get(0), "field 1", "field 2", "class");
        assertRow("report:", result.get(1), "value1", "1.0", Sample.class.toString());
    }

    @Test
    public void should_only_return_properties_specified() {
        ShowVariable showVariable = new ShowVariable(sample);
        table.add(Arrays.asList("field 1"));

        List<List<String>> result = showVariable.doTable(table);

        assertRow("report:", result.get(0), "field 1");
        assertRow("report:", result.get(1), "value1");
    }

    @Test
    public void should_show_invalid_properties_as_errors() {
        ShowVariable showVariable = new ShowVariable(sample);
        table.add(Arrays.asList("invalid property name"));

        List<List<String>> result = showVariable.doTable(table);

        assertRow("report:", result.get(0), "invalid property name");
        assertRow("error:", result.get(1),
                "Method getInvalidPropertyName[0] not found in class fitnesse.slimx.reflection.examples.Sample");
    }

    @Test
    public void should_return_a_row_with_a_null_object_value_if_object_is_null() {
        ShowVariable showVariable = new ShowVariable(null);

        List<List<String>> result = showVariable.doTable(table);

        assertEquals(2, result.size());
        assertRow("report:", result.get(0), Property.unknownName);
        assertRow("report:", result.get(1), Property.nullObjectValue);
    }

    @Test
    public void should_return_all_properties_of_objects_in_list() {
        ShowVariable showVariable = new ShowVariable(samples, "elements");

        List<List<String>> result = showVariable.doTable(table);

        assertRow("report:", result.get(0), "field 1", "field 2", "class");
        assertRow("report:", result.get(1), "value1", "1.0", Sample.class.toString());
    }

    @Test
    public void should_return_a_row_all_properties_for_each_object_in_list() {
        ShowVariable showVariable = new ShowVariable(samples, "elements");

        List<List<String>> result = showVariable.doTable(table);

        assertEquals(3, result.size());
        assertRow("report:", result.get(0), "field 1", "field 2", "class");
        assertRow("report:", result.get(1), "value1", "1.0", Sample.class.toString());
        assertRow("report:", result.get(2), "value1A", "2.0", Sample.class.toString());
    }

    @Test
    public void should_return_rows_with_null_object_values_for_a_null_list() {
        ShowVariable showVariable = new ShowVariable((List<Object>) null, "elements");

        List<List<String>> result = showVariable.doTable(table);

        assertEquals(2, result.size());
        assertRow("report:", result.get(0), Property.unknownName);
        assertRow("report:", result.get(1), Property.nullObjectValue);
    }

    @Test
    public void should_only_return_property_names_for_an_empty_list() {
        ShowVariable showVariable = new ShowVariable(Arrays.asList(), "elements");

        List<List<String>> result = showVariable.doTable(table);

        assertEquals(1, result.size());
    }
}
