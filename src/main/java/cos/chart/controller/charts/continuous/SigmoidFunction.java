package cos.chart.controller.charts.continuous;

import cos.chart.controller.plotting.ContinuousFunctionPlotter;

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
