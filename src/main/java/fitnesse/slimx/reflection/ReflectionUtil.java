package fitnesse.slimx.reflection;

import static util.GracefulNamer.disgrace;
import static util.GracefulNamer.regrace;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {

    public static String gracefulPropertyName(Method getter) {
        String propertyName = getter.getName().substring(3);
        return regrace((propertyName)).toLowerCase();
    }

    public static String disgracefulGetterName(String propertyName) {
        return "get" + disgrace(propertyName);
    }

    public static Method getGetter(Class<?> clazz, String propertyName) throws NoSuchMethodException {
        String getterName = disgracefulGetterName(propertyName);
        return clazz.getMethod(getterName);
    }

    public static List<Method> getGetters(Class<?> clazz) {
        List<Method> getters = new ArrayList<Method>();

        for (Method method : clazz.getMethods())
            if (isGetter(method))
                getters.add(method);

        return getters;
    }

    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get"))
            return false;
        if (method.getName().length() == 3)
            return false;
        if ((method.getModifiers() & Modifier.PUBLIC) == 0)
            return false;
        if (method.getParameterTypes().length > 0)
            return false;
        if (method.getReturnType().equals(void.class))
            return false;

        return true;
    }
}