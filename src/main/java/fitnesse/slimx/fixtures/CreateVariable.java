package fitnesse.slimx.fixtures;

import fitnesse.slim.SystemUnderTest;
import fitnesse.slimx.reflection.VariableClassLoader;

public class CreateVariable {

  @SystemUnderTest
  public Object object;

  public Object create(String className) throws Exception {
    return _create(className);
  }

  public Object create(String className, Object arg1) throws Exception {
    return _create(className, arg1);
  }

  public Object create(String className, Object arg1, Object arg2)
      throws Exception {
    return _create(className, arg1, arg2);
  }

  protected Object _create(String className, Object... args) throws Exception {
    object = VariableClassLoader.instance().newInstance(className, args);
    return object;
  }
}