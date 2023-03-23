package bin.string;

import bin.token.KlassToken;
import bin.variable.Types;
import work.ReplaceWork;

public class Index extends ReplaceWork {
    public Index() {
        super(
                KlassToken.STRING_VARIABLE, KlassToken.INT_VARIABLE, false,
                KlassToken.STRING_VARIABLE
        );
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return klassValue.toString().indexOf(params[0].toString());
    }

    @Override
    public void reset() {}
}
