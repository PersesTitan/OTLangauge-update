package cos.macro.key;

import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.awt.event.KeyEvent;

@RequiredArgsConstructor
public enum AlphaKey implements KeyTool {
    A(KeyEvent.VK_A),
    B(KeyEvent.VK_B),
    C(KeyEvent.VK_C),
    D(KeyEvent.VK_D),
    E(KeyEvent.VK_E),
    F(KeyEvent.VK_F),
    G(KeyEvent.VK_G),
    H(KeyEvent.VK_H),
    I(KeyEvent.VK_I),
    J(KeyEvent.VK_J),
    K(KeyEvent.VK_K),
    L(KeyEvent.VK_L),
    M(KeyEvent.VK_M),
    N(KeyEvent.VK_N),
    O(KeyEvent.VK_O),
    P(KeyEvent.VK_P),
    Q(KeyEvent.VK_Q),
    R(KeyEvent.VK_R),
    S(KeyEvent.VK_S),
    T(KeyEvent.VK_T),
    U(KeyEvent.VK_U),
    V(KeyEvent.VK_V),
    W(KeyEvent.VK_W),
    X(KeyEvent.VK_X),
    Y(KeyEvent.VK_Y),
    Z(KeyEvent.VK_Z)
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
