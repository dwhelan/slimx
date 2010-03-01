package fitnesse.slimx.reflection;

import static fitnesse.slimx.reflection.ReflectionUtil.isGetter;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fitnesse.slimx.reflection.examples.Get;
import fitnesse.slimx.reflection.examples.ReducedVisibility;

public class ReflectionUtilTest {

  @Before
  public void before() {
    VariableClassLoader.instance().clear();
  }

  @After
  public void after() {
    VariableClassLoader.instance().clear();
  }

  @Test
  public void a_public_method_that_starts_with_get_with_no_parameters_should_be_a_getter() throws Exception {
    Method method = Object.class.getMethod("getClass");
    assertTrue(isGetter(method));
  }

  @Test
  public void a_method_that_does_not_start_with_get_should_not_be_a_getter() throws Exception {
    Method method = Object.class.getMethod("toString");
    assertFalse(isGetter(method));
  }

  @Test
  public void a_method_that_does_not_have_any_characters_after_get_should_not_be_a_getter() throws Exception {
    Method method = Get.class.getMethod("get");
    assertFalse(isGetter(method));
  }

  @Test
  public void a_method_that_has_parameters_should_not_be_a_getter() throws Exception {
    Method method = Get.class.getMethod("getObject", Object.class);
    assertFalse(isGetter(method));
  }

  @Test
  public void a_method_that_is_not_public_should_not_be_a_getter() throws Exception {
    Method method = ReducedVisibility.class.getDeclaredMethod("getA");
    assertFalse(isGetter(method));
  }

  @Test
  public void a_method_that_return_void_should_not_be_a_getter() throws Exception {
    Method method = Get.class.getMethod("getVoid");
    assertFalse(isGetter(method));
  }
}
