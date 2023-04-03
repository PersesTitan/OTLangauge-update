package bin.system.console.replace;

import bin.token.KlassToken;
import bin.token.SeparatorToken;
import work.ReplaceWork;

public class NextLine extends ReplaceWork {
    public NextLine() {
        super(KlassToken.SYSTEM, KlassToken.STRING_VARIABLE, true);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return SeparatorToken.SEPARATOR_LINE;
    }
}
