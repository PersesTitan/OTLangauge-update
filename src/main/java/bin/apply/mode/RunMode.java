package bin.apply.mode;

import java.util.concurrent.atomic.AtomicReference;

public enum RunMode {
    NORMAL, SHELL;

    private final static AtomicReference<RunMode> mode = new AtomicReference<>(RunMode.NORMAL);

    public static RunMode getMode() {
        return mode.get();
    }

    public void set() {
        mode.set(this);
    }

    public static boolean getNormal() {
        return mode.get().equals(NORMAL);
    }

    public static boolean getShell() {
        return mode.get().equals(SHELL);
    }
}
