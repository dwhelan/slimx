package fitnesse.slimx.reflection;

import static fitnesse.slimx.reflection.ReflectionUtil.disgracefulGetterName;

import java.lang.reflect.Method;

public class Property {

  public static final String unknownName = "[unknown] - class is unknown";
  public static final String nullObjectValue = "[unknown] - object is null";
  public static final Property Unknown = new Property(unknownName);

  private String name;

  public Property(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Object getValue(Object object) {
    if (object == null)
      return nullObjectValue;

    try {
      Method getter = ReflectionUtil.getGetter(object.getClass(), name);
      return getter.invoke(object);
    } catch (Exception x) {
      return new ErrorValue(errorMessage(object, x));
    }
  }

  private String errorMessage(Object object, Exception x) {
    if (x instanceof NoSuchMethodException)
      return "Method " + disgracefulGetterName(name) + "[0] not found in " + object.getClass();

    return "Could not get property '" + name + "': " + x.getClass().getSimpleName() + "(" + x.getMessage() + ")";
  }
}
