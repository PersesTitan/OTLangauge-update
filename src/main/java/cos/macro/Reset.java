package cos.macro;

import bin.Repository;
import bin.token.KlassToken;
import cos.macro.etc.MacroToken;
import cos.macro.item.MacroItem;
import cos.macro.work.CreateMacro;
import work.ResetWork;

public class Reset implements ResetWork, MacroToken {
    @Override
    public void reset() {
        Repository.createWorks.put(MACRO, new CreateMacro());

        AddWork<MacroItem> addWork = new AddWork<>(MACRO);

        addWork.addS(MOUSE1, MacroItem::clickMouse1);
        addWork.addS(MOUSE2, MacroItem::clickMouse2);
        addWork.addS(MOUSE3, MacroItem::clickMouse3);
        addWork.addS(WHEEL, KlassToken.INT_VARIABLE, MacroItem::mouseWheel);

        addWork.addSR(KlassToken.LIST_INTEGER, GET_POINT, MacroItem::getPointer);
        addWork.addSR(KlassToken.INT_VARIABLE, GET_X, MacroItem::getX);
        addWork.addSR(KlassToken.INT_VARIABLE, GET_Y, MacroItem::getY);
    }
}
