package fitnesse.slimx.reflection;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VariableClassLoaderTest {

  private final String thisFullClassName = getClass().getName();
  private final String thisSimpleClassName = getClass().getSimpleName();
  private final String thisGracefulClassName = "variable class loader test";
  private VariableClassLoader loader = VariableClassLoader.instance();

  @Before
  public void before() {
    loader.clear();
  }

  @After
  public void after() {
    loader.clear();
  }

  @Test
  public void a_valid_full_class_name_should_be_findable() throws ClassNotFoundException {
    assertEquals(getClass(), loader.load(thisFullClassName));
  }

  @Test
  public void a_simple_class_name_should_be_findable_if_its_full_class_name_was_added() throws ClassNotFoundException {
    loader.add(getClass());
    assertEquals(getClass(), loader.load(thisSimpleClassName));
  }

  @Test
  public void a_graceful_class_name_should_be_findable_if_its_full_class_name_was_added()
            throws ClassNotFoundException {
    loader.add(getClass());
    assertEquals(getClass(), loader.load(thisGracefulClassName));
  }

  @Test(expected = ClassNotFoundException.class)
  public void should_be_able_to_remove_a_class() throws ClassNotFoundException {
    loader.add(getClass());
    loader.remove(getClass());
    loader.load(thisSimpleClassName);
  }

  @Test
  public void new_instance_should_create_an_object_with_no_constructor_args() throws Exception {
    assertEquals("", loader.newInstance(String.class.getName()));
  }

  @Test
  public void new_instance_should_create_an_object_with_constructor_args() throws Exception {
    assertEquals("test", loader.newInstance(String.class.getName(), "test"));
  }
}
