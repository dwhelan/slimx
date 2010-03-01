package fitnesse.slimx.fixtures;

import static fitnesse.slimx.reflection.TableUtil.fail;
import static fitnesse.slimx.reflection.TableUtil.pass;
import static fitnesse.slimx.reflection.TableUtil.report;
import static fitnesse.slimx.reflection.TestUtil.assertRow;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fitnesse.slimx.reflection.VariableClassLoader;

public class ImportClassesTest {

  private final ImportClasses importSystem = new ImportClasses();
  private List<List<String>> table = new ArrayList<List<String>>();
  private final String thisPackageName = getClass().getPackage().getName();
  private final String thisSimpleClassName = getClass().getSimpleName();
  private final VariableClassLoader loader = VariableClassLoader.instance();

  @Before
  public void before() {
    loader.clear();
  }

  @After
  public void after() {
    loader.clear();
  }

  @Test
  public void should_fail_an_invalid_package() throws Exception {
    table.add(Arrays.asList("invalid package"));
    List<List<String>> result = importSystem.doTable(table);

    assertEquals(fail("Could not find any classes in package invalid package"), result.get(0).get(0));
  }

  @Test
  public void should_pass_valid_package() throws IOException {
    table.add(Arrays.asList(thisPackageName));
    List<List<String>> result = importSystem.doTable(table);

    assertEquals(pass(thisPackageName), result.get(0).get(0));
  }

  @Test
  public void should_create_blank_package_cell_when_more_than_one_class_is_found() throws Exception {
    table.add(Arrays.asList(thisPackageName));
    List<List<String>> result = importSystem.doTable(table);

    for (int i = 1; i < result.size(); i++)
      assertEquals("", result.get(i).get(0));
  }

  @Test
  public void should_report_imported_classes_for_valid_package() throws Exception {
    table.add(Arrays.asList(thisPackageName));
    List<List<String>> result = importSystem.doTable(table);

    boolean foundClass = false;

    for (List<String> row : result) {
      if (row.get(1).equals(report(thisSimpleClassName)))
        foundClass = true;
    }

    assertTrue(foundClass);
  }

  @Test
  public void should_import_single_class() throws Exception {
    table.add(Arrays.asList(thisPackageName, thisSimpleClassName));
    List<List<String>> result = importSystem.doTable(table);

    assertEquals(1, result.size());
    assertRow(result.get(0), pass(thisPackageName), pass(thisSimpleClassName));
  }

  @Test
  public void should_import_two_classes_in_same_package() throws Exception {
    table.add(Arrays.asList(thisPackageName, thisSimpleClassName));
    table.add(Arrays.asList("", thisSimpleClassName));
    List<List<String>> result = importSystem.doTable(table);

    assertEquals(2, result.size());
    assertRow(result.get(0), pass(thisPackageName), pass(thisSimpleClassName));
    assertRow(result.get(1), "", pass(thisSimpleClassName));
  }

  @Test
  public void importing_a_class_should_make_it_loadable() throws Exception {
    table.add(Arrays.asList(thisPackageName, thisSimpleClassName));
    importSystem.doTable(table);

    assertEquals(getClass(), loader.load(thisSimpleClassName));
  }

  @Test
  public void importing_a_package_should_make_classes_loadable() throws Exception {
    table.add(Arrays.asList(thisPackageName));
    importSystem.doTable(table);

    assertEquals(getClass(), loader.load(thisSimpleClassName));
  }

  @Test(expected = ClassNotFoundException.class)
  public void removing_a_class_should_make_it_unloadable() throws Exception {
    table.add(Arrays.asList(thisPackageName, thisSimpleClassName));
    importSystem.doTable(table);
    table = new ArrayList<List<String>>();
    table.add(Arrays.asList(thisPackageName, "-" + thisSimpleClassName));
    importSystem.doTable(table);

    loader.load(thisSimpleClassName);
  }

  @Test(expected = ClassNotFoundException.class)
  public void removing_a_package_should_make_classes_unloadable() throws Exception {
    table.add(Arrays.asList(thisPackageName));
    importSystem.doTable(table);
    table = new ArrayList<List<String>>();
    table.add(Arrays.asList("-" + thisPackageName));
    importSystem.doTable(table);

    loader.load(thisSimpleClassName);
  }

  @Test
  public void should_report_all_classes_if_none_specified() throws Exception {
    loader.add(String.class);
    loader.add(Double.class);

    importSystem.doTable(table);

    List<List<String>> result = importSystem.doTable(table);

    assertEquals(2, result.size());
    assertRow(result.get(0), report("java.lang"), report("Double"));
    assertRow(result.get(1), report("java.lang"), report("String"));
  }
}
