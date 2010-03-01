package fitnesse.slimx.fixtures;

import static fitnesse.slimx.reflection.TestUtil.assertTable;
import static util.ListUtility.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import fitnesse.slimx.reflection.examples.Sample;

@SuppressWarnings("unchecked")
public class CreateCreateVariableTest {

  private final Sample foo = new Sample("foo", 1.0);
  private final Sample bar = new Sample("bar", 2.0);
  private final List fooBar = new ArrayList(list(foo, bar));
  private List<List<String>> table = new ArrayList<List<String>>();

  @Test
  public void should_generate_wiki_for_properties_specified() {
    table.add(new ArrayList<String>(list("field 1")));
    CreateCheckVariable fixture = new CreateCheckVariable(foo);

    List<List<String>> result = fixture.doTable(table);

    assertTable("report:", result, 
      list("field 1"), 
      list("instructions", "wiki text"), 
      list(CreateCheckVariable.instructions,
        "|query: check variable|<b><i>$variable</b><i>|<br/>" + 
        "|field 1|<br/>" + 
        "|foo|<br/>"));
  }

  @Test
  public void should_generate_wiki_text_for_all_properties_when_no_properties_specified() {
    CreateCheckVariable fixture = new CreateCheckVariable(foo);
    List<List<String>> result = fixture.doTable(table);

    assertTable("report:", result,
      list("instructions", "wiki text"),
      list(CreateCheckVariable.instructions,
        "|query: check variable|<b><i>$variable</b><i>|<br/>" + 
        "|field 1|field 2|class|<br/>" + 
        "|foo|1.0|" + foo.getClass().toString() + "|<br/>"));
  }

  @Test
  public void should_generate_wiki_text_for_list_elements_with_properties_specified() {
    table.add(new ArrayList<String>(Arrays.asList("field 1")));
    CreateCheckVariable fixture = new CreateCheckVariable(fooBar, "elements");
    List<List<String>> result = fixture.doTable(table);

    assertTable("report:", result,
      list("field 1"),
      list("instructions", "wiki text"), 
      list(CreateCheckVariable.instructions,
        "|query: check variable|<b><i>$variable</b><i>|elements|<br/>" + 
        "|field 1|<br/>" + "|foo|<br/>" + "|bar|<br/>"));
  }

  @Test
  public void should_generate_wiki_text_for_list_elements_with_all_properties_when_none_specified() {
    CreateCheckVariable fixture = new CreateCheckVariable(fooBar, "elements");

    List<List<String>> result = fixture.doTable(table);

    assertTable("report:", result,
      list("instructions", "wiki text"), 
      list(CreateCheckVariable.instructions,
        "|query: check variable|<b><i>$variable</b><i>|elements|<br/>" + 
        "|field 1|field 2|class|<br/>" + 
        "|foo|1.0|" + foo.getClass().toString() + "|<br/>" + 
        "|bar|2.0|" + foo.getClass().toString() + "|<br/>"));
  }
}