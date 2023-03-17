package bin.system.console;

import bin.token.KlassToken;
import work.ReplaceWork;

public class Scanner extends ReplaceWork {
    private final java.util.Scanner scanner = new java.util.Scanner(System.in);

    public Scanner() {
        super(KlassToken.SYSTEM, true);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return this.scanner.nextLine();
    }

    @Override
    public void reset() {}
}
