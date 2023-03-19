package bin.token;

import bin.apply.mode.DebugMode;

import java.util.Locale;

public interface SeparatorToken {
    boolean isWindow = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win");

    String SEPARATOR_FILE = System.getProperty("file.separator");              // /
    String SEPARATOR_HOME = System.getProperty("user.home");                   // /User/name
    String SEPARATOR_LINE = System.lineSeparator();                            // \n \r
    String MODULE_EXTENSION = ".otlm";
    String SYSTEM_EXTENSION = ".otls";

    String[] extensions = {".otl", ".otlanguage"};

    String COMPULSION = "compulsion";   // 강제
    String ALTERATION = "alteration";   // 변경
    String OPERATE = "operate";         // 동작
    String MODULE = "module";

    // /User/name/.otl
    String INSTALL_PATH = DebugMode.isDevelopment()
            ? getPath(SEPARATOR_HOME, ".otl")
            : System.getenv().getOrDefault("OTL_HOME", getPath(SEPARATOR_HOME, ".otl"));

    // [a, b, c] => a/b/c
    static String getPath(String...line) {
        return String.join(SEPARATOR_FILE, line);
    }

    static boolean isExtension(String file) {
        file = file.toLowerCase(Locale.ROOT);
        for (String extension : extensions) {
            if (file.endsWith(extension)) return true;
        }
        return false;
    }
}
