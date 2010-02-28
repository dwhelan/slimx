package fitnesse.slimx.fixtures;

import static fitnesse.slimx.reflection.TestUtil.assertRow;
import static org.junit.Assert.assertEquals;
import static util.ListUtility.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import fitnesse.slimx.reflection.examples.Sample;

public class CreateCreateVariableTest {

    private final Sample foo = new Sample("foo", 1.0);
    private final Sample bar = new Sample("bar", 2.0);
    private CreateCheckVariable creator = new CreateCheckVariable(foo);
    private List<List<String>> table = new ArrayList<List<String>>();

    @Test
    public void should_report_original_table() {
        table.add(new ArrayList<String>(list("a1", "b1")));
        table.add(new ArrayList<String>(list("a2", "b2")));

        List<List<String>> result = creator.doTable(table);

        assertRow("report:", result.get(0), "a1", "b1");
        assertRow("report:", result.get(1), "a2", "b2");
    }

    @Test
    public void should_provide_header_row() {
        List<List<String>> result = creator.doTable(table);

        assertRow("report:", result.get(0), "instructions", "wiki text");
    }

    @Test
    public void should_provide_instructions() {
        List<List<String>> result = creator.doTable(table);

        assertEquals("report:" + CreateCheckVariable.instructions, result.get(1).get(0));
    }

    @Test
    public void should_generate_wiki_text_for_all_properties_when_no_properties_specified() {
        List<List<String>> result = creator.doTable(table);

        assertEquals("report:" + "|query: check variable|<b><i>$variable</b><i>|<br/>" + "|field 1|field 2|class|<br/>"
                + "|foo|1.0|" + foo.getClass().toString() + "|<br/>", result.get(1).get(1));
    }

    @Test
    public void should_generate_wiki_text_for_all_specified_properties() {
        table.add(new ArrayList<String>(Arrays.asList("class")));

        List<List<String>> result = creator.doTable(table);

        assertEquals("report:" + "|query: check variable|<b><i>$variable</b><i>|<br/>" + "|class|<br/>" + "|"
                + foo.getClass().toString() + "|<br/>", result.get(2).get(1));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_generate_wiki_text_for_variable_list_elements() {
        table.add(new ArrayList<String>(Arrays.asList("field 1")));
        List fooBar = new ArrayList(list(foo, bar));
        CreateCheckVariable creator = new CreateCheckVariable(fooBar, "elements");

        List<List<String>> result = creator.doTable(table);

        assertEquals("report:" + "|query: check variable|<b><i>$variable</b><i>|elements|<br/>" + "|field 1|<br/>"
                + "|foo|<br/>" + "|bar|<br/>", result.get(2).get(1));
    }
}
