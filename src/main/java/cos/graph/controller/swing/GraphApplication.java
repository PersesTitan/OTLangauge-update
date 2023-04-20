package cos.graph.controller.swing;

import cos.graph.controller.plotting.Graph;
import cos.graph.controller.plotting.PlotSettings;

import javax.swing.*;
import java.awt.*;

public class GraphApplication implements SettingsUpdateListener {
    protected JTextField minX, minY, maxX, maxY;

    public GraphApplication(JFrame frame, Graph graph) {
//        GraphPanel graphPanel = new InteractiveGraphPanel(this);
        GraphPanel graphPanel = new GraphPanel();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        PlotSettings plot = graph.plotSettings;

//        final double rMinX = plot.getMinX();
//        final double rMaxX = plot.getMaxX();
//        final double rMinY = plot.getMinY();
//        final double rMaxY = plot.getMaxY();
//        minX = new JTextField(String.valueOf(rMinX));
//        minY = new JTextField(String.valueOf(rMinY));
//        maxX = new JTextField(String.valueOf(rMaxX));
//        maxY = new JTextField(String.valueOf(rMaxY));

//        panel.add(new JLabel("X: ("));
//        panel.add(minX);
//        panel.add(new JLabel(","));
//        panel.add(maxX);
//        panel.add(new JLabel(") , Y: ("));
//        panel.add(minY);
//        panel.add(new JLabel(", "));
//        panel.add(maxY);
//        panel.add(new JLabel(")"));

//        JButton reset = new JButton("RESET");
//        reset.addActionListener(e -> {
//            plot.setMaxX(rMaxX);
//            plot.setMinX(rMinX);
//            plot.setMaxY(rMaxY);
//            plot.setMinY(rMinY);
//            graphUpdated(plot);
//            frame.repaint();
//        });
//        panel.add(reset);

        Container c = frame.getContentPane();
        c.add(panel, BorderLayout.SOUTH);
        c.add(graphPanel, BorderLayout.CENTER);
        graphPanel.setGraph(graph);
    }

    @Override
    public void graphUpdated(PlotSettings settings) {
        minX.setText(String.valueOf(settings.getMinX()));
        minY.setText(String.valueOf(settings.getMinY()));
        maxX.setText(String.valueOf(settings.getMaxX()));
        maxY.setText(String.valueOf(settings.getMaxY()));
    }
}
