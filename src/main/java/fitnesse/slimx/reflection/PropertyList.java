package fitnesse.slimx.reflection;

import static fitnesse.slimx.reflection.ReflectionUtil.gracefulPropertyName;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PropertyList {

  private List<Property> properties = new ArrayList<Property>();

  public PropertyList(Class<?> clazz) {
    init(clazz, null);
  }

  public PropertyList(Class<?> clazz, List<List<String>> table) {
    List<String> names = table.size() > 0 ? table.get(0) : null;
    init(clazz, names);
  }

  private void init(Class<?> clazz, List<String> names) {
    if (clazz == null && names == null)
      properties.add(Property.Unknown);
    else {
      if (names == null)
        names = getNamesFrom(clazz);

      for (String name : names)
        properties.add(new Property(name));
    }
  }

  public List<Property> getProperties() {
    return properties;
  }

  public List<String> getNames() {
    List<String> names = new ArrayList<String>();

    for (Property property : properties)
      names.add(property.getName());

    return names;
  }

  public List<List<Object>> getValues(List<Object> objects) {
    List<List<Object>> values = new ArrayList<List<Object>>();

    if (objects == null)
      values.add(getValues((Object) null));
    else
      for (Object object : objects)
        values.add(getValues(object));

    return values;
  }

  private List<Object> getValues(Object object) {
    List<Object> values = new ArrayList<Object>();

    for (Property property : properties)
      values.add(property.getValue(object));

    return values;
  }

  private static List<String> getNamesFrom(Class<?> clazz) {
    List<String> names = new ArrayList<String>();

    if (clazz == null)
      names.add(Property.unknownName);
    else
      for (Method method : ReflectionUtil.getGetters(clazz))
        names.add(gracefulPropertyName(method));

    return names;
  }
}