package fitnesse.slimx.junit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.io.File;

import fitnesse.junit.JUnitHelper;
import fitnesse.responders.run.JavaFormatter;

public class JUnitFitNesseRunner {

  private JUnitHelper helper = new JUnitHelper(".", new File("FitNesseRoot/files/testResults", "junit")
      .getAbsolutePath());

  protected void runTest(String testName) {
    try {
      helper.assertTestPasses(testName);
    } catch (Exception x) {
      x.printStackTrace();
      fail("Test failed: " + x.getMessage());
    }
    assertTestsRun(testName);
  }

  protected void runSuite(String suiteName) {
    try {
      helper.assertSuitePasses(suiteName);
    } catch (Exception x) {
      x.printStackTrace();
      fail("Suite failed: " + x.getMessage());
    }
    assertTestsRun(suiteName);
  }

  protected void assertTestsRun(String name) {
    JavaFormatter formatter = JavaFormatter.getInstance(name);

    assertEquals(1, formatter.getTestsExecuted().size());
  }
}
