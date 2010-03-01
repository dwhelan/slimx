package fitnesse.slimx.fixtures;

import static fitnesse.slimx.reflection.TableUtil.error;
import static fitnesse.slimx.reflection.TableUtil.report;
import static fitnesse.slimx.reflection.TestUtil.assertTable;
import static util.ListUtility.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import fitnesse.slimx.reflection.Property;
import fitnesse.slimx.reflection.examples.Sample;

@SuppressWarnings("unchecked")
public class ShowVariableTest {

  private static final Object foo = new Sample("foo", 1.0);

  private final ArrayList<List<String>> table = new ArrayList<List<String>>();
  private ShowVariable fixture;

  @Test
  public void should_return_all_properties() {
    fixture = new ShowVariable(foo);

    assertDoTableResults("report:",
            list("field 1", "field 2", "class"),
            list("foo", "1.0", Sample.class.toString()));
  }

  @Test
  public void should_only_return_properties_specified() {
    fixture = new ShowVariable(foo);
    table.add(list("field 1"));

    assertDoTableResults("report:",
            list("field 1"),
            list("foo"));
  }

  @Test
  public void should_show_invalid_properties_as_errors() {
    fixture = new ShowVariable(foo);
    table.add(Arrays.asList("invalid property name"));

    assertDoTableResults(
        "",
            list(report("invalid property name")),
            list(error("Method getInvalidPropertyName[0] not found in class fitnesse.slimx.reflection.examples.Sample")));
  }

  @Test
  public void should_return_a_single_unknown_column_if_object_is_null() {
    fixture = new ShowVariable(null);

    assertDoTableResults("report:",
            list(Property.unknownName),
            list(Property.nullObjectValue));
  }

  @Test
  public void should_return_a_row_with_all_properties_for_each_object_in_a_list() {
    Object bar = new Sample("bar", 2.0);
    List<Object> fooBar = Arrays.asList(foo, bar);
    fixture = new ShowVariable(fooBar, "elements");

    assertDoTableResults("report:",
            list("field 1", "field 2", "class"),
            list("foo", "1.0", Sample.class.toString()),
            list("bar", "2.0", Sample.class.toString()));
  }

  @Test
  public void should_return_a_single_unknown_column_if_list_is_null() {
    fixture = new ShowVariable((List<Object>) null, "elements");

    assertDoTableResults("report:",
            list(Property.unknownName),
            list(Property.nullObjectValue));
  }

  @Test
  public void should_return_a_single_unknown_column_if_list_is_empty() {
    fixture = new ShowVariable(Arrays.asList(), "elements");

    assertDoTableResults("report:",
            list(Property.unknownName));
  }

  private void assertDoTableResults(String prefix, List<String>... expected) {
    assertTable(prefix, fixture.doTable(table), expected);
  }
}