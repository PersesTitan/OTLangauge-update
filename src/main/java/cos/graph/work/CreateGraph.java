package cos.graph.work;

import bin.apply.mode.DebugMode;
import bin.exception.FileException;
import bin.token.KlassToken;
import bin.token.SeparatorToken;
import cos.graph.etc.GraphToken;
import cos.graph.item.GraphItem;
import work.CreateWork;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

public class CreateGraph extends CreateWork<GraphItem> {
    private static boolean useGraph = false;

    public CreateGraph() {
        super(
                GraphItem.class, GraphToken.GRAPH,
                KlassToken.DOUBLE_VARIABLE, KlassToken.DOUBLE_VARIABLE,
                KlassToken.DOUBLE_VARIABLE, KlassToken.DOUBLE_VARIABLE
        );
    }

    @Override
    protected Object createItem(Object[] params) {
        if (!CreateGraph.useGraph) {
            System.setProperty("java.awt.headless", "false");
            String iconPath = DebugMode.isDevelopment()
                    ? SeparatorToken.getPath(Objects.requireNonNull(this.getClass().getResource("gui")).getPath(), "icon.otlm")
                    : SeparatorToken.getPath(SeparatorToken.INSTALL_PATH, SeparatorToken.MODULE, "gui", "icon.otlm");
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(iconPath))) {
                Taskbar.getTaskbar().setIconImage(((ImageIcon) inputStream.readObject()).getImage());
            } catch (IOException | ClassNotFoundException e) {
                throw FileException.CREATE_ICON_ERROR.getThrow(null);
            }
            CreateGraph.useGraph = true;
        }
        double number1 = (double) params[0];
        double number2 = (double) params[1];
        double number3 = (double) params[2];
        double number4 = (double) params[3];
        return new GraphItem(number1, number2, number3, number4);
    }
}
