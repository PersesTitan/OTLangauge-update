package bin.system.console.replace;

import bin.token.KlassToken;
import work.ReplaceWork;

public class Space extends ReplaceWork {
    public Space() {
        super(KlassToken.SYSTEM, KlassToken.STRING_VARIABLE, true);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return " ";
    }
}
