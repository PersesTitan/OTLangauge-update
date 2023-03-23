package bin.string;

import bin.token.KlassToken;
import work.ReplaceWork;
import work.StartWork;

public class Contains extends ReplaceWork {
    public Contains() {
        super(KlassToken.STRING_VARIABLE, KlassToken.BOOL_VARIABLE, false, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return klassValue.toString().contains(params[0].toString());
    }

    @Override
    public void reset() {}
}
