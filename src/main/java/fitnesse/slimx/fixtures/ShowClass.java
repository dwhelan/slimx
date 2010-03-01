package fitnesse.slimx.fixtures;

import static fitnesse.slimx.reflection.TableUtil.report;
import static fitnesse.slimx.reflection.TableUtil.reportRow;

import java.util.ArrayList;
import java.util.List;

import fitnesse.slimx.reflection.PropertyList;
import fitnesse.slimx.reflection.VariableClassLoader;

public class ShowClass {

  private List<Class<?>> classes = new ArrayList<Class<?>>();

  public ShowClass(String className) throws ClassNotFoundException {
    classes.add(VariableClassLoader.instance().load(className));
  }

  public ShowClass() {
    classes.addAll(VariableClassLoader.instance().classes());
  }

  public List<List<String>> doTable(List<List<String>> table) {

    List<List<String>> result = new ArrayList<List<String>>();

    for (Class<?> clazz : classes)
      result.add(reportClass(clazz));

    return result;
  }

  private List<String> reportClass(Class<?> clazz) {
    List<String> propertyRow = new ArrayList<String>();
    propertyRow.add(report(clazz.getName()));
    propertyRow.add(report("properties"));
    propertyRow.addAll(reportRow(new PropertyList(clazz).getNames()));
    return propertyRow;
  }
}
