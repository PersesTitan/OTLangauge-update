package cos.macro.key;

import java.awt.*;

public interface KeyTool {
    void keyPress(Robot robot);
    void keyRelease(Robot robot);
    void click(Robot robot);
}
