package cos.macro.key;

import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.awt.event.KeyEvent;

@RequiredArgsConstructor
public enum FunctionKey implements KeyTool {
    F1(KeyEvent.VK_F1),
    F2(KeyEvent.VK_F2),
    F3(KeyEvent.VK_F3),
    F4(KeyEvent.VK_F4),
    F5(KeyEvent.VK_F5),
    F6(KeyEvent.VK_F6),
    F7(KeyEvent.VK_F7),
    F8(KeyEvent.VK_F8),
    F9(KeyEvent.VK_F9),
    F10(KeyEvent.VK_F10),
    F11(KeyEvent.VK_F11),
    F12(KeyEvent.VK_F12),
    F13(KeyEvent.VK_F13),
    F14(KeyEvent.VK_F14),
    F15(KeyEvent.VK_F15),
    F16(KeyEvent.VK_F16),
    F17(KeyEvent.VK_F17),
    F18(KeyEvent.VK_F18),
    F19(KeyEvent.VK_F19),
    F20(KeyEvent.VK_F20),
    F21(KeyEvent.VK_F21),
    F22(KeyEvent.VK_F22),
    F23(KeyEvent.VK_F23),
    F24(KeyEvent.VK_F24)
    ;

    private final int code;

    @Override
    public void keyPress(Robot robot) {
        robot.keyPress(this.code);
    }

    @Override
    public void keyRelease(Robot robot) {
        robot.keyRelease(this.code);
    }

    @Override
    public void click(Robot robot) {
        robot.keyPress(this.code);
        robot.delay(robot.getAutoDelay());
        robot.keyRelease(this.code);
    }
}
