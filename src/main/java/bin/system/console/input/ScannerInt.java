package bin.system.console.input;

import bin.Setting;
import bin.token.KlassToken;
import work.ReplaceWork;

public class ScannerInt extends ReplaceWork {
    public ScannerInt() {
        super(KlassToken.SYSTEM, KlassToken.INT_VARIABLE, false);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return Setting.scanner.nextInt();
    }
}
