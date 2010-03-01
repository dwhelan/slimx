package fitnesse.slimx.fixtures;

import org.junit.Test;

import fitnesse.slimx.junit.JUnitFitNesseRunner;

@SuppressWarnings("unused")
public class Debug extends JUnitFitNesseRunner {
  
  // Uncomment any of these @Test attributes and then debug the corresponding
  // test method if you want to debug an acceptance test.
  
  // @Test
  public void access_variable() throws Exception {
    runTest("AccessVariable");
  }
  
  // @Test
  public void check_variable() throws Exception {
    runTest("CheckVariable");
  }
  
  // @Test
  public void create_check_variable() throws Exception {
    runTest("CreateCheckVariable");
  }
  
  // @Test
  public void create_variable() throws Exception {
    runTest("CreateVariable");
  }
  
  // @Test
  public void import_classes() throws Exception {
    runTest("ImportClasses");
  }

  // @Test
  public void show_class() throws Exception {
    runTest("ShowClass");
  }

  // @Test
  public void show_variable() throws Exception {
    runTest("ShowVariable");
  }

  @Override
  protected void runTest(String page) {
    super.runTest("SlimX" + '.' + page);
  }
}
