package cos.macro.key;

import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.awt.event.KeyEvent;

@RequiredArgsConstructor
public enum NumberKey implements KeyTool {
    K0(KeyEvent.VK_0),
    K1(KeyEvent.VK_1),
    K2(KeyEvent.VK_2),
    K3(KeyEvent.VK_3),
    K4(KeyEvent.VK_4),
    K5(KeyEvent.VK_5),
    K6(KeyEvent.VK_6),
    K7(KeyEvent.VK_7),
    K8(KeyEvent.VK_8),
    K9(KeyEvent.VK_9)
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
