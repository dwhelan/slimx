package com.intellimec.fitnesse.junit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.io.File;

import fitnesse.junit.JUnitHelper;
import fitnesse.responders.run.JavaFormatter;

public class JUnitFitNesseRunner {

    private JUnitHelper helper = new JUnitHelper(".", new File("target", "junit-fitnesse").getAbsolutePath());

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
        
        // We expect 2 tests because the Suite SetUp is counted as a test
        assertEquals(2, formatter.getTestsExecuted().size());
    }
}
