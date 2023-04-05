package bin.string;

import bin.token.KlassToken;
import bin.variable.Types;
import work.ReplaceWork;

public class SubString extends ReplaceWork {
    public SubString() {
        super(
                KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE, false,
                KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE
        );
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return Types.toString(klassValue).substring((int) params[0], (int) params[1]);
    }

    @Override
    public void reset() {}
}
