package cos.graph.controller.plotting;

import java.awt.*;

public interface Plotter {
    String getName();
    void plot(Graph p, Graphics g, int chartWidth, int chartHeight);
}
