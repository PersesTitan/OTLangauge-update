package cos.macro.key;

import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.awt.event.KeyEvent;

@RequiredArgsConstructor
public enum SpecialKey implements KeyTool {
    ENTER(KeyEvent.VK_ENTER),
    BACK_SPACE(KeyEvent.VK_BACK_SPACE),
    TAB(KeyEvent.VK_TAB),
    CANCEL(KeyEvent.VK_CANCEL),
    CLEAR(KeyEvent.VK_CLEAR),
    SHIFT(KeyEvent.VK_SHIFT),
    CONTROL(KeyEvent.VK_CONTROL),
    ALT(KeyEvent.VK_ALT),
    PAUSE(KeyEvent.VK_PAUSE),
    CAPS_LOCK(KeyEvent.VK_CAPS_LOCK),
    SPACE(KeyEvent.VK_SPACE)
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
        robot.setAutoDelay(robot.getAutoDelay());
        robot.keyRelease(this.code);
    }
}
