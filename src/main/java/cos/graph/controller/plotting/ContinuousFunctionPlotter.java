package cos.graph.controller.plotting;

import java.awt.*;

public abstract class ContinuousFunctionPlotter implements Plotter {
    public abstract double getY(double x);

    @Override
    public void plot(Graph graph, Graphics g, int chartWidth, int chartHeight) {
        double prevX = 0, prevY = 0;
        boolean first = true;
        double xRange = graph.plotSettings.getRangeX();
        for (int ax = 0; ax < chartWidth; ax++) {
            double x = graph.plotSettings.getMinX() + ((ax / (double) chartWidth) * xRange);
            double y = getY(x);
            if (!first && y <= graph.plotSettings.getMaxY() && y >= graph.plotSettings.getMinY()) graph.drawLine(g, prevX, prevY, x, y);
            prevX = x;
            prevY = y;
            first = false;
        }
    }
}
