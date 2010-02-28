package fitnesse.slimx.fixtures;

import static fitnesse.slimx.reflection.ShowUtil.fail;
import static fitnesse.slimx.reflection.ShowUtil.pass;
import static fitnesse.slimx.reflection.ShowUtil.report;
import static util.ListUtility.list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fitnesse.slimx.reflection.PackageClassFinder;
import fitnesse.slimx.reflection.VariableClassLoader;

public class ImportClasses {

    private List<List<String>> result;
    private String packageName;
    private String className;

    private boolean removeClass;
    private boolean removePackage;

    public List<List<String>> doTable(List<List<String>> table) throws IOException {
        result = new ArrayList<List<String>>();

        if (table.size() == 0)
            reportClasses();
        else
            for (List<String> row : table)
                importPackage(row);

        return result;
    }

    private void reportClasses() {
        for (Class<?> clazz : VariableClassLoader.instance().classes())
            reportClass(clazz);
    }

    private void reportClass(Class<?> clazz) {
        result.add(list(report(clazz.getPackage().getName()), report(clazz.getSimpleName())));
    }

    private void parsePackage(String cell) {
        if (cell.length() == 0)
            return;

        if (cell.startsWith("-")) {
            removePackage = true;
            packageName = cell.substring(1);
        } else {
            removePackage = false;
            packageName = cell;
        }
    }

    private void parseClass(String cell) {
        if (cell.startsWith("-")) {
            removeClass = true;
            className = cell.substring(1);
        } else {
            removeClass = false;
            className = cell;
        }
    }

    private void importPackage(List<String> row) throws IOException {
        parsePackage(row.get(0));

        if (row.size() > 1)
            findClass(row);
        else
            findClasses();
    }

    private void findClasses() throws IOException {
        List<Class<?>> classes = new PackageClassFinder(packageName).findClasses();

        if (classes.size() == 0)
            result.add(list(fail("Could not find any classes in package", packageName)));

        boolean first = true;
        for (Class<?> clazz : classes) {
            if (first)
                result.add(list(pass(packageName), report(clazz.getSimpleName())));
            else
                result.add(list("", report(clazz.getSimpleName())));
            process(clazz);
            first = false;
        }
    }

    private void findClass(List<String> row) {
        parseClass(row.get(1));

        try {
            Class<?> clazz = new PackageClassFinder(packageName).findClass(className);
            String packageResult = row.get(0).length() > 0 ? pass(row.get(0)) : "";
            result.add(list(packageResult, pass(className)));
            process(clazz);
        } catch (Exception x) {
            result.add(list(fail(packageName), fail(className, x.getMessage())));
        }
    }

    private void process(Class<?> clazz) {
        if (removeClass || removePackage)
            VariableClassLoader.instance().remove(clazz);
        else
            VariableClassLoader.instance().add(clazz);
    }
}