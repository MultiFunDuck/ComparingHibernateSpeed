package Main;

public interface ClassWithMeasuredFunction {
    @MeasuredFunction(name = "measuredFunction")
    public void measuredFunction();
}
