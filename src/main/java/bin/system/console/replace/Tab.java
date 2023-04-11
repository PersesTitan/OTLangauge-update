package bin.system.console.replace;

import bin.token.KlassToken;
import work.ReplaceWork;

public class Tab extends ReplaceWork {
    public Tab() {
        super(KlassToken.SYSTEM, KlassToken.STRING_VARIABLE, true);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return "\t";
    }
}
