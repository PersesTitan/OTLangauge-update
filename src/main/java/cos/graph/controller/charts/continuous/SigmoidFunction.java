package cos.graph.controller.charts.continuous;

import cos.graph.controller.plotting.ContinuousFunctionPlotter;

public class SigmoidFunction extends ContinuousFunctionPlotter {
    @Override
    public double getY(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    @Override
    public String getName() {
        return "Sigmoid Function";
    }
}
