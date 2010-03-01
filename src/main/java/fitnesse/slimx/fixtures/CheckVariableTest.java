package fitnesse.slimx.fixtures;

import static org.junit.Assert.assertEquals;
import static util.ListUtility.list;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import fitnesse.slimx.reflection.examples.Sample;

@SuppressWarnings("unchecked")
public class CheckVariableTest {
  private static final Sample foo = new Sample("foo", 1.0);
  private static final Sample bar = new Sample("bar", 2.0);
  private static final List fooBar = Arrays.asList(foo, bar);

  @Test
  public void should_return_a_list_of_fields_for_each_property_in_a_variable() {
    List<List<List<Object>>> list = new CheckVariable(foo).query();

    assertEquals(1, list.size());
    assertField(list.get(0), list("field 1", "foo"), list("field 2", 1.0),
        list("class", foo.getClass()));
  }

  @Test
  public void should_return_a_list_of_fields_for_each_property_of_each_object_in_a_list() {
    List<List<List<Object>>> list = new CheckVariable(fooBar, "elements")
        .query();

    assertEquals(2, list.size());
    assertField(list.get(0), list("field 1", "foo"), list("field 2", 1.0),
        list("class", foo.getClass()));
    assertField(list.get(1), list("field 1", "bar"), list("field 2", 2.0),
        list("class", bar.getClass()));
  }

  private void assertField(List<List<Object>> fields, List<?>... expectedFields) {
    assertEquals(expectedFields.length, fields.size());

    for (int i = 0; i < fields.size(); i++) {
      List<Object> field = fields.get(i);
      List<?> expectedField = expectedFields[i];
      assertEquals(2, field.size());
      assertEquals(expectedField.get(0), field.get(0));
      assertEquals(expectedField.get(1), field.get(1));
    }
  }
}
