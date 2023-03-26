package cos.gui.item;

import bin.apply.loop.LoopFunction;
import cos.gui.etc.EventTool;

import javax.swing.*;

public class PassewordFieldItem extends JPasswordField implements EventTool {
    @Override
    public void addEvent(String endLine, LoopFunction function) {
        this.addEvent(this, endLine, function);
    }
}
