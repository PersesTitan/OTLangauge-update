package cos.gui.item;

import bin.variable.Types;
import bin.variable.custom.CustomList;
import cos.color.item.ColorItem;

import java.awt.*;
import java.awt.event.ActionListener;

public interface ComponentTool {
    String getText();
    String getName();
    Color getBackground();
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    boolean contains(int x, int y);
    boolean isEnabled();
    boolean isVisible();
    CustomList<Integer> getSizeList();
    CustomList<Integer> getLocationList();

    Component add(Component component);
    void setText(String text);
    void setName(String name);
    void setBackground(Color bg);
    void setEnabled(boolean b);
    void setVisible(boolean aFlag);
    void setLocation(int x, int y);
    void setSize(int width, int height);
    void setFont(Font font);
    void addActionListener(ActionListener l);

    default void setBG(ColorItem color) {
        this.setBackground(color.getColor());
    }

    default ColorItem getBG() {
        return new ColorItem(getBackground());
    }

    default void setX(int x) {
        this.setLocation(x, getY());
    }

    default void setY(int y) {
        this.setLocation(getX(), y);
    }

    default void setWidth(int width) {
        this.setSize(width, getHeight());
    }

    default void setHeight(int height) {
        this.setSize(getWidth(), height);
    }

    default CustomList<Integer> getSizeList(Component component) {
        CustomList<Integer> list = new CustomList<>(Types.INTEGER);
        Dimension dimension = component.getSize();
        list.add(dimension.width);
        list.add(dimension.height);
        return list;
    }

    default CustomList<Integer> getLocationList(Component component) {
        CustomList<Integer> list = new CustomList<>(Types.INTEGER);
        Point point = component.getLocation();
        list.add(point.x);
        list.add(point.y);
        return list;
    }

    
}
