package cos.gui.item;

import bin.token.Token;
import bin.variable.custom.CustomList;
import cos.gui.etc.GuiException;
import cos.gui.etc.GuiToken;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PanelItem extends JPanel implements ComponentTool {
    @Override
    public CustomList<Integer> getSizeList() {
        return getSizeList(this);
    }

    @Override
    public CustomList<Integer> getLocationList() {
        return getLocationList(this);
    }

    @Override
    public String getText() {
        throw GuiException.DO_NOT_USE_TYPE.getThrow(GuiToken.GET_TEXT);
    }

    @Override
    public void setText(String text) {
        throw GuiException.DO_NOT_USE_TYPE.getThrow(GuiToken.SET_TEXT);
    }

    @Override
    public void addActionListener(ActionListener l) {
        throw GuiException.DO_NOT_USE_TYPE.getThrow(GuiToken.ADD_EVENT);
    }

    @Override
    public String toString() {
        return GuiToken.GUI + Token.PARAM_S + GuiToken.PANEL + Token.PARAM_E;
    }
}
