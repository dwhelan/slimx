package fitnesse.slimx.fixtures;

import static fitnesse.slimx.reflection.TableUtil.reportRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fitnesse.slimx.reflection.Property;
import fitnesse.slimx.reflection.PropertyList;

public abstract class ObjectTable {

  public Object object;
  private List<Object> objectList;
  private String qualifier;
  private Class<?> clazz;

  protected ObjectTable(Object object) {
    this.object = object;
    this.objectList = (List<Object>) Arrays.asList(object);
    if (object != null)
      clazz = object.getClass();
  }

  public ObjectTable(List<Object> objectList, String qualifier) {
    this.object = objectList;
    this.objectList = objectList;
    this.qualifier = qualifier;
    if (objectList != null && objectList.size() > 0 && objectList.get(0) != null)
      clazz = objectList.get(0).getClass();
  }

  Class<?> getObjectClass() {
    return clazz;
  }

  public String getQualifier() {
    return qualifier;
  }

  protected List<Object> objectList() {
    return objectList;
  }

  public List<List<List<Object>>> query() {
    List<List<List<Object>>> table = new ArrayList<List<List<Object>>>();

    for (Object object : objectList())
      table.add(query(object));

    return table;
  }

  protected List<List<Object>> query(Object object) {
    List<List<Object>> row = new ArrayList<List<Object>>();

    if (object != null)
      for (Property property : new PropertyList(clazz).getProperties())
        row.add(Arrays.asList(property.getName(), property.getValue(object)));

    return row;
  }

  public List<List<String>> doTable(List<List<String>> table) {
    PropertyList properties = new PropertyList(clazz, table);

    List<List<String>> result = new ArrayList<List<String>>();

    result.add(reportRow(properties.getNames()));

    for (List<Object> row : properties.getValues(objectList()))
      result.add(reportRow(row));

    return result;
  }

  public String toString() {
    return object.toString();
  }

  public int hashCode() {
    return object.hashCode();
  }

  public boolean equals(Object other) {
    return object.equals(other);
  }

  public Class<?> getVariableClass() {
    return object.getClass();
  }
}
