package bin.string.regexp;

import bin.token.KlassToken;
import work.ReplaceWork;

public class ReplaceAll extends ReplaceWork {
    public ReplaceAll() {
        super(
                KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE, false,
                KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE
        );
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return klassValue.toString().replaceAll(params[0].toString(), params[1].toString());
    }

    @Override
    public void reset() {}
}
