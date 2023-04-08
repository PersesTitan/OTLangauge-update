package cos.graph.controller.charts.discrete;

import cos.graph.controller.plotting.DiscreteFunctionPlotter;
import cos.graph.controller.plotting.Graph;

import java.awt.*;

public class CandleStickPlot extends DiscreteFunctionPlotter {
    public static double COLUMN_WIDTH = 1.0d;
    public static Color BACKGROUND = new Color(220, 220, 220, 128);
    public static Color LINE_COLOUR = Color.BLACK;

    public CandleStickPlot(String[] labels, double[] highs, double[] lows, double[] means) {
        super(labels, highs, lows, means);
    }

    @Override
    public String getName() {
        return "Candle Stick Plot";
    }

    @Override
    public void plot(Graph p, Graphics g, int chartWidth, int chartHeight) {
        for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
            p.drawCandleStick(g, COLUMN_WIDTH, columnIndex, getHigh(columnIndex), getMean(columnIndex), getLow(columnIndex), LINE_COLOUR, BACKGROUND);
        }
    }
}
