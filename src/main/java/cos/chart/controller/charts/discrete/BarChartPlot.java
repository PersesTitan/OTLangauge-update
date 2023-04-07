package cos.chart.controller.charts.discrete;

import cos.chart.controller.plotting.DiscreteFunctionPlotter;
import cos.chart.controller.plotting.Graph;

import java.awt.*;

public class BarChartPlot extends DiscreteFunctionPlotter {
    public static double COLUMN_WIDTH = 1;

    public BarChartPlot(String[] labels, double[] highs, double[] lows, double[] means) {
        super(labels, highs, lows, means);
    }

    @Override
    public String getName() {
        return "Bar Chart";
    }

    @Override
    public void plot(Graph p, Graphics g, int chartWidth, int chartHeight) {
        for (int x = 0; x < getColumnCount(); x++) {
            p.drawBar(g, COLUMN_WIDTH, x, getHigh(x), Color.RED);
            p.drawBar(g, COLUMN_WIDTH, x, getLow(x), Color.BLUE);
        }
    }
}
