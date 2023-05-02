package cos.macro.item;

import bin.variable.Types;
import bin.variable.custom.CustomList;
import cos.macro.etc.MacroException;
import lombok.Getter;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

@Getter
public class MacroItem {
    private final Robot robot;
    public MacroItem() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw MacroException.DO_NOT_CONTROL.getThrow(null);
        }
    }

    public void moveMouse(int x, int y) {
        this.robot.mouseMove(x, y);
    }

    public void clickMouse1() {
        this.click(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void clickMouse2() {
        this.click(InputEvent.BUTTON2_DOWN_MASK);
    }

    public void clickMouse3() {
        this.click(InputEvent.BUTTON3_DOWN_MASK);
    }

    public void mouseWheel(int wheelAmt) {
        this.robot.mouseWheel(wheelAmt);
    }

    private void click(int buttons) {
        this.robot.mousePress(buttons);
        this.robot.delay(this.robot.getAutoDelay());
        this.robot.mouseRelease(buttons);
    }

    public static CustomList<Integer> getPointer() {
        Point point = MouseInfo.getPointerInfo().getLocation();
        return new CustomList<>(Types.INTEGER, List.of(point.x, point.y));
    }

    public static int getX() {
        return MouseInfo.getPointerInfo().getLocation().x;
    }

    public static int getY() {
        return MouseInfo.getPointerInfo().getLocation().y;
    }
}
