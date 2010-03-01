package fitnesse.slimx.fixtures;

import static fitnesse.slimx.reflection.TestUtil.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fitnesse.slimx.reflection.VariableClassLoader;
import fitnesse.slimx.reflection.examples.Sample;

import static util.ListUtility.list;

@SuppressWarnings("unchecked")
public class ShowClassTest {

  private final VariableClassLoader loader = VariableClassLoader.instance();

  @Before
  public void before() {
    loader.clear();
  }

  @After
  public void after() {
    loader.clear();
  }

  @Test(expected = ClassNotFoundException.class)
  public void should_throw_if_invalid_class_name() throws ClassNotFoundException {
    new ShowClass("invalid class name");
  }

  @Test
  public void should_report_properties_of_a_single_class() throws ClassNotFoundException {
    ShowClass showClass = new ShowClass(Sample.class.getName());
    List<List<String>> table = showClass.doTable(null);

    assertTable("report:", table,
      list(Sample.class.getName(), "properties", "field 1", "field 2", "class"));
  }

  @Test
  public void should_report_properties_of_all_imported_classes() throws ClassNotFoundException {
    loader.add(Sample.class);
    loader.add(String.class);
    ShowClass showClass = new ShowClass();
    List<List<String>> table = showClass.doTable(null);

    assertTable("report:", table,
      list(Sample.class.getName(), "properties", "field 1", "field 2", "class"),
      list(String.class.getName(), "properties", "bytes", "class"));
  }
}
