package cos.color;

import bin.Repository;
import cos.color.item.ColorItem;
import cos.color.item.ColorToken;
import cos.color.work.CreateColor;
import cos.color.work.SetColor;
import work.ResetWork;

public class Reset implements ResetWork, ColorToken {
    @Override
    public void reset() {
        Repository.createWorks.put(COLOR, new CreateColor());
        Repository.startWorks.put(COLOR, SET_COLOR, new SetColor());

        AddWork<ColorItem> addWork = new AddWork<>(COLOR);
        addWork.addS(SET_R, i, ColorItem::setRed);
        addWork.addS(SET_G, i, ColorItem::setGreen);
        addWork.addS(SET_B, i, ColorItem::setBlue);
        addWork.addS(SET_A, i, ColorItem::setAlpha);

        addWork.addR(i, GET_R, ColorItem::getRed);
        addWork.addR(i, GET_G, ColorItem::getGreen);
        addWork.addR(i, GET_B, ColorItem::getBlue);
        addWork.addR(i, GET_A, ColorItem::getAlpha);
        addWork.addR(li, GET_COLOR, ColorItem::getColors);
    }
}
