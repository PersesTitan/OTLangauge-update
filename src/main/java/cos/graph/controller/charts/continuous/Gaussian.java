package cos.graph.controller.charts.continuous;

import cos.graph.controller.plotting.ContinuousFunctionPlotter;

public class Gaussian extends ContinuousFunctionPlotter {
    protected double stdDeviation, variance, mean;

    public Gaussian(double stdDeviation, double mean) {
        this.stdDeviation = stdDeviation;
        variance = stdDeviation * stdDeviation;
        this.mean = mean;
    }

    @Override
    public double getY(double x) {
        return Math.pow(Math.exp(-(((x - mean) * (x - mean)) / ((2 * variance)))), 1 / (stdDeviation * Math.sqrt(2 * Math.PI)));
    }

    @Override
    public String getName() {
        return "Gaussian Curve";
    }
}
