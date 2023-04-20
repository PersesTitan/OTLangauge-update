package cos.graph.controller.swing;

import cos.graph.controller.plotting.Graph;
import cos.graph.etc.GraphException;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@Getter
public class GraphPanel extends JPanel {
    protected Graph graph;

    public void setGraph(Graph graph) {
        this.graph = graph;
        repaint();
    }

    public void paintComponent(Graphics g) {
       super.paintComponent(g);
       if (graph != null) {
           if (graph.functions.size() == 0) throw GraphException.NO_HAVE_ITEM.getThrow(null);
           else graph.draw(g, getWidth(), getHeight());
       }
    }

    public BufferedImage getImage() {
       return graph != null ? graph.getImage(getWidth(), getHeight()) : null;
    }
}
