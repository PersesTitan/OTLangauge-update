package cos.gui.work;

import bin.exception.SystemException;
import bin.exception.VariableException;
import cos.gui.etc.GuiToken;
import work.CreateWork;

import java.awt.event.ActionEvent;

public class CreateEvent extends CreateWork<ActionEvent> {
    public CreateEvent() {
        super(ActionEvent.class, GuiToken.EVENT);
    }

    @Override
    protected Object createItem(Object[] params) {
        throw VariableException.DO_NOT_CREATE_KLASS.getThrow(null);
    }
}
