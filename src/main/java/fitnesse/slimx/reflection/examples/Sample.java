package fitnesse.slimx.reflection.examples;

public class Sample {

  private String field1;
  private double field2;

  public Sample() {
  }

  public Sample(String field1, double field2) {
    this.field1 = field1;
    this.field2 = field2;
  }

  public Sample(String field1, Double field2) {
    this.field1 = field1;
    this.field2 = field2;
  }

  public Sample(String field1) {
    this.field1 = field1;
  }

  public String getField1() {
    return field1;
  }

  public void setField1(String field1) {
    this.field1 = field1;
  }

  public double getField2() {
    return field2;
  }

  public void setField(double field2) {
    this.field2 = field2;
  }

  public void copy(Sample other) {
    field1 = other.field1;
    field2 = other.field2;
  }

  public boolean equals(Object other) {
    if (!(other instanceof Sample))
      return false;

    Sample otherSample = (Sample) other;

    return otherSample.field1.equals(field1) && otherSample.field2 == field2;
  }

  public String toString() {
    return field1 + ", " + field2;
  }
}