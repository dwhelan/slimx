package fitnesse.slimx.fixtures;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CreateVariableTest {
    @Test
    public void should_create_an_object_with_a_no_args_constructor() throws Exception {
        assertEquals("", new CreateVariable().create(String.class.getName()));
    }

    @Test
    public void should_create_an_object_with_a_single_arg_constructor() throws Exception {
        assertEquals("foo", new CreateVariable().create(String.class.getName(), "foo"));
    }
}
