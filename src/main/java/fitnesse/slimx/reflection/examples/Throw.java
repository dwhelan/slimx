package fitnesse.slimx.reflection.examples;

public class Throw {
    private Throwable exception;

    public Throw(Throwable exception) {
        this.exception = exception;
    }

    public Object getException() throws Throwable {
        throw exception;
    }
}