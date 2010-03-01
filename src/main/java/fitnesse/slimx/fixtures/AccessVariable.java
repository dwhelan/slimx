package fitnesse.slimx.fixtures;

import fitnesse.slim.SystemUnderTest;

public class AccessVariable extends ObjectTable {

  @SystemUnderTest
  public Object object;

  public AccessVariable(Object object) {
    super(object);
    this.object = object;
  }
}