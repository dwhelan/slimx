package fitnesse.slimx.reflection;

import java.util.Collection;
import java.util.List;

public interface ReflectionListerInterface {

    List<List<Object>> list(Class<?> clazz);

    List<List<List<Object>>> list(Collection<?> objects);

    List<List<List<Object>>> list(Collection<?> objects, Collection<String> aliases);

    List<List<Object>> list(Object object);

    List<List<Object>> list(Object object, Collection<String> aliases);
}
