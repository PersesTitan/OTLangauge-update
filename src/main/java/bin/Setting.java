package bin;

import bin.apply.mode.DebugMode;
import bin.token.ColorToken;

import java.util.concurrent.atomic.AtomicReference;

public class Setting {
    public static void runMessage(String errorLine) {
        warringMessage(String.format("경고! %s는 실행되지 않은 라인 입니다.", errorLine));
    }

    public static void warringMessage(String message) {
        if (DebugMode.WARRING.check())
            System.out.printf("%s%s%s\n", ColorToken.YELLOW, message, ColorToken.RESET);
    }

    public static void errorMessage(String message) {
        if (DebugMode.ERROR.check())
            System.out.printf("%s%s%s\n", ColorToken.RED, message, ColorToken.RESET);
    }
}
