package cos.chart.controller.charts.continuous;

import cos.chart.controller.plotting.ContinuousFunctionPlotter;

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
