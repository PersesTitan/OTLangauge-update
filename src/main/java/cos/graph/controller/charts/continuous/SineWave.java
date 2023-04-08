package cos.graph.controller.charts.continuous;

import cos.graph.controller.plotting.ContinuousFunctionPlotter;

public class SineWave extends ContinuousFunctionPlotter {
    @Override
    public double getY(double x) {
        return Math.sin(x);
    }

    @Override
    public String getName() {
        return "Sine Wave";
    }
}
