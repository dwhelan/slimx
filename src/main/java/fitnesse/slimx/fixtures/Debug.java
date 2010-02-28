package fitnesse.slimx.fixtures;

import com.intellimec.fitnesse.junit.JUnitFitNesseRunner;

public class Debug extends JUnitFitNesseRunner {

    // @Test
    public void show_class() throws Exception {
        runTest("ShowClass");
    }

    // @Test
    public void show_variable() throws Exception {
        runTest("ShowVariable");
    }

    // @Test
    public void access_variable() throws Exception {
        runTest("AccessVariable");
    }

    // @Test
    public void check_variable() throws Exception {
        runTest("CheckVariable");
    }

    // @Test
    public void create_variable() throws Exception {
        runTest("CreateVariable");
    }

    // @Test
    public void import_classes() throws Exception {
        runTest("ImportClasses");
    }

    @Override
    protected void runTest(String page) {
        super.runTest("DriveSyncAcceptanceTests.SupportTables" + '.' + page);
    }
}
