package fitnesse.slimx.reflection;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageClassFinder {

    private List<Class<?>> classes = new ArrayList<Class<?>>();
    private String packageName;
    private String packagePath;
    private ClassFileNameMatcher matcher;

    public PackageClassFinder(String packageName) {
        this.packageName = packageName;
        packagePath = packageName.replace(".", "/");
        matcher = new ClassFileNameMatcher();
    }

    public List<Class<?>> findClasses() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packagePath);

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();

            if (url.getProtocol() == "file")
                findClassesInFileSystem(url);

            else if (url.getProtocol() == "jar")
                findClassesInJar(url);
        }

        return classes;
    }

    private void findClassesInFileSystem(URL url) throws IOException {
        String directory = URLDecoder.decode(url.getFile(), "UTF-8");

        for (File file : new File(directory).listFiles()) {
            if (file.isFile())
                try {
                    findClass(getClassName(file));
                } catch (ClassNotFoundException x) {
                }
        }
    }

    private void findClassesInJar(URL url) throws IOException {
        JarURLConnection conn = (JarURLConnection) url.openConnection();
        JarFile jarfile = conn.getJarFile();
        Enumeration<JarEntry> entries = jarfile.entries();

        while (entries.hasMoreElements()) {
            String filePath = entries.nextElement().getName();

            if (matcher.matches(filePath))
                try {
                    findClass(matcher.className());
                } catch (ClassNotFoundException x) {
                }
        }
    }

    public Class<?> findClass(String className) throws ClassNotFoundException {
        Class<?> clazz = null;

        String fullClassName = packageName + "." + className;

        try {
            clazz = Class.forName(fullClassName);
        } catch (ExceptionInInitializerError e) {
            clazz = Class.forName(fullClassName, false, Thread.currentThread().getContextClassLoader());
        }

        if (isInstantiable(clazz))
            classes.add(clazz);

        return clazz;
    }

    private static boolean isInstantiable(Class<?> clazz) {
        return !clazz.isInterface();
    }

    private static String getClassName(File file) {
        return file.getName().substring(0, file.getName().length() - 6);
    }

    class ClassFileNameMatcher {
        private final Pattern pattern;
        private Matcher matcher;

        ClassFileNameMatcher() {
            StringBuilder patternString = new StringBuilder("^");
            if (packagePath.length() > 0)
                patternString.append(packagePath).append('/');
            patternString.append("(\\w+)\\.class$");

            pattern = Pattern.compile(patternString.toString());
        }

        boolean matches(String path) {
            matcher = pattern.matcher(path);
            return matcher.matches();
        }

        String className() {
            return matcher.group(1);
        }
    }
}