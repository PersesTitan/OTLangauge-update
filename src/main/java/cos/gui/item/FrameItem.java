package cos.gui.item;

import bin.token.Token;
import bin.variable.custom.CustomList;
import cos.gui.etc.GuiException;
import cos.gui.etc.GuiToken;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FrameItem extends JFrame implements ComponentTool {
    public FrameItem() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public CustomList<Integer> getSizeList() {
        return getSizeList(this);
    }

    @Override
    public CustomList<Integer> getLocationList() {
        return getLocationList(this);
    }

    @Override
    public void setText(String text) {
        super.setTitle(text);
    }

    @Override
    public String getText() {
        return super.getTitle();
    }

    @Override
    public void addActionListener(ActionListener l) {
        throw GuiException.DO_NOT_USE_TYPE.getThrow(GuiToken.ADD_EVENT);
    }

    @Override
    public String toString() {
        return GuiToken.GUI + Token.PARAM_S + GuiToken.FRAME + Token.PARAM_E;
    }
}
