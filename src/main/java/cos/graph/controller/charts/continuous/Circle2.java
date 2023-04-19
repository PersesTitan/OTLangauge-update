package cos.graph.controller.charts.continuous;

import cos.graph.controller.plotting.ContinuousFunctionPlotter;

public class Circle2 extends ContinuousFunctionPlotter {
    @Override
    public String getName() {
        return "Circle";
    }

    @Override
    public double getY(double x) {
        return -(x * x);
    }
}
