package cos.poison.etc;

import bin.token.ColorToken;
import bin.token.Token;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface PoisonToken {
    String POISON = "ㅍㅇㅍ";

    char RESPONSE_TYPE    = '>';  // json, text, html, ...
    char DATA_TYPE        = '<';  // json, url

    String DELETE_COOKIE = "-ㅋㄱㅋ";
    String SET_COOKIE = "<ㅋㄱㅋ";
    String GET_COOKIE = ">ㅋㄱㅋ";
    String IS_EMPTY_COOKIE = "?ㅋㄱㅋ";
    String REDIRECT = "ㄹㄷㄹ";

    String SET_TYPE = "<ㅌㅍㅌ";
    String SET_DATA = "<ㄷㅌㄷ";

    String SET_HOST = "<ㅎㅅㅎ";
    String SET_PORT = "<ㅍㅌㅍ";
    String CREATE = "ㅅㅇㅅ";
    String START = "ㅅㅌㅅ";
    String STOP = "ㄲㅌㄲ";

    String POST     = "ㅍㅅㅍ";
    String GET      = "ㄱㅅㄱ";
    String PATCH    = "ㅂㅅㅂ";
    String PUT      = "ㅌㅅㅌ";
    String DELETE   = "ㄷㅅㄷ";

    static void printTime() {
        String time = new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(new Date());
        System.out.printf("%s[%s]%s", ColorToken.YELLOW, time, ColorToken.RESET);
    }

    static void printMessage(String message) {
        printTime();
        System.out.println(ColorToken.GREEN + message + ColorToken.RESET);
    }

    default void printStart() {
        System.out.printf(
                """
                %s╭───────────╮╭──╮╭────────╮╭──────────╮╭─────────╮%s
                %s│  ╭─────╮  │╰──╯│  ╭─────╯│  ╭────╮  ││  ╭───╮  │%s
                %s│  ╰─────╯  │╭──╮│  ╰─────╮│  │    │  ││  │   │  │%s
                %s│  ┌────────╯│  │╰─────╮  ││  │    │  ││  │   │  │%s
                %s│  │ ╭──────╮│  │╭─────╯  ││  ╰────╯  ││  │   │  │%s
                %s│  │ │ ╭──╮ ││  │╰────────╯╰──────────╯╰──╯   ╰──╯%s
                %s│  │ │ ╰──╯ ││  │%s        %s== OTLanguage ==%s
                %s╰──╯ ╰──────╯╰──╯%s        %s==   Poison   ==%s

                """,
                ColorToken.PURPLE_BRIGHT, ColorToken.RESET, ColorToken.PURPLE_BRIGHT, ColorToken.RESET,
                ColorToken.PURPLE_BRIGHT, ColorToken.RESET, ColorToken.PURPLE_BRIGHT, ColorToken.RESET,
                ColorToken.PURPLE_BRIGHT, ColorToken.RESET, ColorToken.PURPLE_BRIGHT, ColorToken.RESET,
                ColorToken.PURPLE_BRIGHT, ColorToken.RESET, ColorToken.PURPLE, ColorToken.RESET,
                ColorToken.PURPLE_BRIGHT, ColorToken.RESET, ColorToken.PURPLE, ColorToken.RESET);
    }

//                  _____      _
//                 |  __ \\    (_)
//                 | |__) |__  _ ___  ___  _ __
//                 |  ___/ _ \\| / __|/ _ \\| '_ \\
//                 | |  | (_) | \\__ \\ (_) | | | |
//                 |_|   \\___/|_|___/\\___/|_| |_|

//                ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗
//                ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║
//                ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║
//                ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║
//                ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║
//                ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝
}
