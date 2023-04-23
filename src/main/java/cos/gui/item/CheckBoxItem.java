package cos.gui.item;

import bin.token.Token;
import bin.variable.custom.CustomList;
import cos.gui.etc.GuiToken;

import javax.swing.*;

public class CheckBoxItem extends JCheckBox implements ComponentTool {
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
        return GuiToken.GUI + Token.PARAM_S + GuiToken.CHECK_BOX + Token.PARAM_E;
    }
}
