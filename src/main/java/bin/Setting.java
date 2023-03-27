package bin;

import bin.apply.mode.DebugMode;
import bin.token.ColorToken;

import java.util.Scanner;

public class Setting {
    public static final Scanner scanner = new Scanner(System.in);

    public static void runMessage(String errorLine) {
        warringMessage(String.format("경고! %s는 실행되지 않은 라인 입니다.", errorLine));
    }

    public static void infoMessage(String message) {
        System.out.println(ColorToken.GREEN + message + ColorToken.RESET);
    }

    public static void warringMessage(String message) {
        if (DebugMode.WARRING.check()) System.out.println(ColorToken.YELLOW + message + ColorToken.RESET);
    }

    public static void errorMessage(String message) {
        if (DebugMode.ERROR.check()) System.out.println(ColorToken.RED + message + ColorToken.RESET);
    }
}
