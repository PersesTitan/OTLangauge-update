package cos.gui.item;

import bin.token.Token;
import bin.variable.custom.CustomList;
import cos.gui.etc.GuiToken;

import javax.swing.*;

public class TextFieldItem extends JTextField implements ComponentTool {
    @Override
    public CustomList<Integer> getSizeList() {
        return getSizeList(this);
    }

    @Override
    public CustomList<Integer> getLocationList() {
        return getLocationList(this);
    }

    @Override
    public String toString() {
        return GuiToken.GUI + Token.PARAM_S + GuiToken.TEXT_FIELD + Token.PARAM_E;
    }
}
