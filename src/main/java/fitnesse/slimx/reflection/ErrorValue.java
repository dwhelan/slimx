package fitnesse.slimx.reflection;

public class ErrorValue {

  private String message;

  public ErrorValue(String message) {
    this.message = message;
  }

  public String toString() {
    return message;
  }
}
