package cos.color.item;

import bin.token.Token;
import bin.variable.Types;
import bin.variable.custom.CustomList;
import lombok.Getter;

import java.awt.*;

@Getter
public class ColorItem {
    private Color color;

    public ColorItem(int r, int g, int b, int a) {
        checkColorValue(r);
        checkColorValue(g);
        checkColorValue(b);
        checkColorValue(a);
        this.color = new Color(r, g, b, a);
    }

    public ColorItem(Color color) {
        this.color = color;
    }

    private void checkColorValue(int value) {
        if (value < 0 || value > 255) throw ColorException.COLOR_VALUE_ERROR.getThrow(value);
    }

    public int getRed() {
        return color.getRed();
    }

    public int getGreen() {
        return color.getGreen();
    }

    public int getBlue() {
        return color.getBlue();
    }

    public int getAlpha() {
        return color.getAlpha();
    }

    public void setColor(int r, int g, int b, int a) {
        checkColorValue(r);
        checkColorValue(g);
        checkColorValue(b);
        checkColorValue(a);
        this.color = new Color(r, g, b, a);
    }

    public void setRed(int r) {
        checkColorValue(r);
        this.color = new Color(r, getGreen(), getBlue(), getAlpha());
    }

    public void setGreen(int g) {
        checkColorValue(g);
        this.color = new Color(getRed(), g, getBlue(), getAlpha());
    }

    public void setBlue(int b) {
        checkColorValue(b);
        this.color = new Color(getRed(), getGreen(), b, getAlpha());
    }

    public void setAlpha(int a) {
        checkColorValue(a);
        this.color = new Color(getRed(), getGreen(), getBlue(), a);
    }

    public CustomList<Integer> getColors() {
        CustomList<Integer> list = new CustomList<>(Types.INTEGER);
        list.add(getRed());
        list.add(getGreen());
        list.add(getBlue());
        list.add(getAlpha());
        return list;
    }

    @Override
    public String toString() {
        String r = Integer.toString(getRed());
        String g = Integer.toString(getGreen());
        String b = Integer.toString(getBlue());
        String a = Integer.toString(getAlpha());
        return ColorToken.COLOR + Token.PARAM_S + String.join(Token.PARAM_SE, r, g, b, a) + Token.PARAM_E;
    }
}
