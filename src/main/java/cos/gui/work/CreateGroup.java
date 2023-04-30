package cos.gui.work;

import cos.gui.etc.GuiToken;
import work.CreateWork;

import javax.swing.*;

public class CreateGroup extends CreateWork<ButtonGroup> {
    public CreateGroup() {
        super(ButtonGroup.class, GuiToken.GROUP);
    }

    @Override
    protected Object createItem(Object[] params) {
        return new ButtonGroup();
    }
}
