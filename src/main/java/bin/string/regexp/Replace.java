package bin.string.regexp;

import bin.token.KlassToken;
import work.ReplaceWork;

public class Replace extends ReplaceWork {
    public Replace() {
        super(KlassToken.STRING_VARIABLE, false, KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return klassValue.toString().replace(params[0].toString(), params[1].toString());
    }

    @Override
    public void reset() {}
}
