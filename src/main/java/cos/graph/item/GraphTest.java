package cos.graph.item;

import bin.exception.Error;
import cos.graph.controller.charts.continuous.SineWave;
import cos.gui.item.FrameItem;

import javax.swing.*;

public class GraphTest {
    public static void main(String[] args) {
        FrameItem frame = new FrameItem();
//        JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GraphItem item = new GraphItem(-2, 2, -1, 1);
//        item.setColor(new ColorItem());
        item.setGridSpacingX(0.5);
        item.setGridSpacingY(0.5);
        item.add(new SineWave());
        item.createAdd(frame);

        frame.setVisible(true);
        try {

        } catch (Error e) {
            e.print();
            e.printStackTrace();
        }
    }
}
