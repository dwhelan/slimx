package fitnesse.slimx.reflection;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import util.GracefulNamer;
import fitnesse.slim.SlimExtensions;

public class VariableClassLoader {

    private final static VariableClassLoader instance = new VariableClassLoader();
    private final Map<String, Class<?>> classMap = new TreeMap<String, Class<?>>();

    private VariableClassLoader() {
    }

    public static VariableClassLoader instance() {
        return instance;
    }

    public void clear() {
        classMap.clear();
    }

    public Collection<Class<?>> classes() {
        return classMap.values();
    }

    public Class<?> load(String className) throws ClassNotFoundException {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
        }

        String disgracefulClassName = GracefulNamer.disgrace(className);
        Class<?> clazz = classMap.get(disgracefulClassName);
        if (clazz != null)
            return clazz;

        throw new ClassNotFoundException(className);
    }

    public void add(Class<?> clazz) {
        classMap.put(clazz.getSimpleName(), clazz);
    }

    public void remove(Class<?> clazz) {
        classMap.remove(clazz.getSimpleName());
    }

    public Object newInstance(String className, Object... args) throws Exception {
        Class<?> clazz = load(className);
        Object[] convertedArgs = new Object[args.length];

        for (Constructor<?> constructor : clazz.getConstructors()) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            if (parameterTypes.length != args.length)
                continue;

            try {
                for (int i = 0; i < args.length; i++)
                    convertedArgs[i] = convertArg(args[i], parameterTypes[i]);
            } catch (IllegalArgumentException x) {
                continue;
            }

            return clazz.getConstructor(parameterTypes).newInstance(convertedArgs);
        }

        return null;
    }

    private Object convertArg(Object arg, Class<?> parameterType) throws IllegalArgumentException {
        if (parameterType.isInstance(arg))
            return arg;

        if (arg == null) {
            if (parameterType.isPrimitive())
                throw new IllegalArgumentException();
            return null;
        }

        if (arg instanceof String)
            return SlimExtensions.convertFromString((String) arg, parameterType);

        throw new IllegalArgumentException();
    }
}
