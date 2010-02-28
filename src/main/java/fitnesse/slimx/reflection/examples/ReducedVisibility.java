package fitnesse.slimx.reflection.examples;

public class ReducedVisibility {
    protected Object getA() {
        return null;
    }

    @SuppressWarnings("unused")
    private Object getB() {
        return null;
    }

    Object getC() {
        return null;
    }
}