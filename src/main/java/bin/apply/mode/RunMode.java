package bin.apply.mode;

import java.util.concurrent.atomic.AtomicReference;

public enum RunMode {
    Normal, Shell;

    private final static AtomicReference<RunMode> mode = new AtomicReference<>(RunMode.Normal);

    public void set() {
        mode.set(this);
    }

    public static boolean isNormal() {
        return mode.get().equals(Normal);
    }

    public static boolean isShell() {
        return mode.get().equals(Shell);
    }
}
