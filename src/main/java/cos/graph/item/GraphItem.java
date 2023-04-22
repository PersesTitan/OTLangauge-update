package cos.graph.item;

import bin.apply.calculator.number.NumberTool;
import bin.token.Token;
import cos.color.item.ColorItem;
import cos.graph.controller.plotting.ContinuousFunctionPlotter;
import cos.graph.controller.plotting.Graph;
import cos.graph.controller.plotting.PlotSettings;
import cos.graph.controller.plotting.Plotter;
import cos.graph.controller.swing.GraphApplication;
import cos.graph.etc.GraphToken;
import cos.gui.item.FrameItem;

public class GraphItem extends NumberTool {
    private final PlotSettings plotSettings;
    private final Graph graph;

    public GraphItem(double xMin, double xMax, double yMin, double yMax) {
        this.plotSettings = new PlotSettings(xMin, xMax, yMin, yMax);
        this.graph = new Graph(plotSettings);
    }

    public void setColor(ColorItem color) {
        this.plotSettings.setPlotColor(color.getColor());
    }

    public void setGridSpacingX(double x) {
        this.plotSettings.setGridSpacingX(x);
    }

    public void setGridSpacingY(double y) {
        this.plotSettings.setGridSpacingY(y);
    }

    public void setGridSpacing(double x, double y) {
        this.plotSettings.setGridSpacingX(x);
        this.plotSettings.setGridSpacingY(y);
    }

    public void setTitle(String title) {
        this.plotSettings.setTitle(title);
    }

    public void addOfficial(ContinuousFunctionPlotter function) {
        graph.functions.add(function);
    }

    public void add(Plotter plotter) {
        graph.functions.add(plotter);
    }

    public void createAdd(FrameItem frame) {
        new GraphApplication(frame, this.graph);
    }

    @Override
    public String toString() {
        String xMin = Double.toString(plotSettings.getMinX());
        String xMax = Double.toString(plotSettings.getMaxX());
        String yMin = Double.toString(plotSettings.getMinY());
        String yMax = Double.toString(plotSettings.getMaxY());
        return GraphToken.GRAPH + Token.PARAM_S + String.join(Token.PARAM_SE, xMin, xMax, yMin, yMax) + Token.PARAM_E;
    }
}
