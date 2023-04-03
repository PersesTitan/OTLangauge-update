package bin.system.console;

import bin.Setting;
import bin.token.KlassToken;
import work.ReplaceWork;

public class Scanner extends ReplaceWork {
    public Scanner() {
        super(KlassToken.SYSTEM, KlassToken.STRING_VARIABLE, true);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return Setting.scanner.nextLine();
    }
}
