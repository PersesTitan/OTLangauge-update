package cos.gui;

import bin.Repository;
import cos.gui.etc.GuiToken;
import cos.gui.item.ComponentTool;
import cos.gui.work.CreateEvent;
import cos.gui.work.CreateGUI;
import cos.gui.work.CreateGroup;
import work.ResetWork;

public class Reset implements ResetWork, GuiToken {
    @Override
    public void reset() {
        checkModuleError("color");

        Repository.createWorks.put(GUI, new CreateGUI());
        Repository.createWorks.put(GROUP, new CreateGroup());
        Repository.createWorks.put(EVENT, new CreateEvent());

        AddWork<ComponentTool> addWork = new AddWork<>(GUI);

        addWork.addR(s, GET_TEXT, ComponentTool::getText);
        addWork.addR(s, GET_NAME, ComponentTool::getName);
        addWork.addR(i, GET_X, ComponentTool::getX);
        addWork.addR(i, GET_Y, ComponentTool::getY);
        addWork.addR(i, GET_WIDTH, ComponentTool::getWidth);
        addWork.addR(i, GET_HEIGHT, ComponentTool::getHeight);
        addWork.addR(b, GET_VISIBLE, ComponentTool::isVisible);
        addWork.addR(b, GET_ENABLE, ComponentTool::isEnabled);
        addWork.addR(li, GET_SIZE, ComponentTool::getSizeList);
        addWork.addR(li, GET_LOCATION, ComponentTool::getLocationList);
        addWork.addR(COLOR, GET_BG, ComponentTool::getBG);

        addWork.addS(SET_TEXT, s, ComponentTool::setText);
        addWork.addS(SET_NAME, s, ComponentTool::setName);
        addWork.addS(SET_X, i, ComponentTool::setX);
        addWork.addS(SET_Y, i, ComponentTool::setY);
        addWork.addS(SET_WIDTH, i, ComponentTool::setWidth);
        addWork.addS(SET_HEIGHT, i, ComponentTool::setHeight);
        addWork.addS(SET_VISIBLE, b, ComponentTool::setVisible);
        addWork.addS(SET_ENABLE, b, ComponentTool::setEnabled);
        addWork.addS(SET_SIZE, i, i, ComponentTool::setSize);
        addWork.addS(ADD, GUI, ComponentTool::add);
        addWork.addS(SET_BG, COLOR, ComponentTool::setBG);

//        Repository.createWorks.put(ACTION_EVENT, new CreateEvent());
//
//        Repository.createWorks.put(FRAME, new CreateFrame());
//        Repository.createWorks.put(PANEL, new CreatePanel());
//
//        Repository.createWorks.put(BUTTON, new CreateButton());
//        Repository.createWorks.put(CHECK_BOX, new CreateCheckBox());
//        Repository.createWorks.put(RADIO_BUTTON, new CreateRadioButton());
//        Repository.createWorks.put(TEXT_FIELD, new CreateTextField());
//        Repository.createWorks.put(PASSWORD_FILED, new CreatePasswordField());
//        Repository.createWorks.put(TEXT_AREA, new CreateTextArea());
//
//        new ComboPair<Integer>(COMBO_BOX_INT, Types.INTEGER);
//        new ComboPair<Long>(COMBO_BOX_LONG, Types.LONG);
//        new ComboPair<Boolean>(COMBO_BOX_BOOL, Types.BOOLEAN);
//        new ComboPair<String>(COMBO_BOX_STRING, Types.STRING);
//        new ComboPair<Character>(COMBO_BOX_CHARACTER, Types.CHARACTER);
//        new ComboPair<Float>(COMBO_BOX_FLOAT, Types.FLOAT);
//        new ComboPair<Double>(COMBO_BOX_DOUBLE, Types.DOUBLE);
//
//        Set<String> NOT_EVENT = Set.of(
//                TEXT_AREA,
//                COMBO_BOX_INT, COMBO_BOX_LONG, COMBO_BOX_BOOL, COMBO_BOX_STRING,
//                COMBO_BOX_CHARACTER, COMBO_BOX_FLOAT, COMBO_BOX_DOUBLE
//        );
//        Set<String> NOT_TEXT = Set.of(
//                COMBO_BOX_INT, COMBO_BOX_LONG, COMBO_BOX_BOOL, COMBO_BOX_STRING,
//                COMBO_BOX_CHARACTER, COMBO_BOX_FLOAT, COMBO_BOX_DOUBLE
//        );
//        Set<String> TOKEN = Set.of(
//                BUTTON, CHECK_BOX, RADIO_BUTTON, TEXT_FIELD, PASSWORD_FILED, TEXT_AREA,
//                COMBO_BOX_INT, COMBO_BOX_LONG, COMBO_BOX_BOOL, COMBO_BOX_STRING,
//                COMBO_BOX_CHARACTER, COMBO_BOX_FLOAT, COMBO_BOX_DOUBLE
//        );
//
//        TOKEN.forEach(v -> {
//            if (!NOT_EVENT.contains(v)) Repository.loopWorks.put(v, ADD_EVENT, new EventAddEvent(v));
//            if (!NOT_TEXT.contains(v)) {
//                Repository.startWorks.put(v, SET_TEXT, new SetText(v));
//                Repository.replaceWorks.put(KlassToken.STRING_VARIABLE, v, GET_TEXT, new GetText(v));
//            }
//
//            Repository.startWorks.put(v, SET_SIZE, new SetSize(v));
//            Repository.startWorks.put(v, SET_VISIBLE, new SetVisible(v));
//            Repository.startWorks.put(v, SET_ENABLED, new SetEnabled(v));
//
//            Repository.replaceWorks.put(KlassToken.INT_VARIABLE, v, GET_X, new GetX(v));
//            Repository.replaceWorks.put(KlassToken.INT_VARIABLE, v, GET_Y, new GetY(v));
//            Repository.replaceWorks.put(KlassToken.INT_VARIABLE, v, GET_HEIGHT, new GetHeight(v));
//            Repository.replaceWorks.put(KlassToken.INT_VARIABLE, v, GET_WIDTH, new GetWidth(v));
//            Repository.replaceWorks.put(KlassToken.BOOL_VARIABLE, v, GET_VISIBLE, new IsVisible(v));
//            Repository.replaceWorks.put(KlassToken.BOOL_VARIABLE, v, GET_ENABLED, new IsEnable(v));
//        });
    }
}
