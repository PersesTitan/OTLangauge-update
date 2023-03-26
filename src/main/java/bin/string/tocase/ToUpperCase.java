package bin.string.tocase;

import bin.token.KlassToken;
import work.ReplaceWork;

import java.util.Locale;

public class ToUpperCase extends ReplaceWork {
    public ToUpperCase() {
        super(KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE, false);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return klassValue.toString().toUpperCase(Locale.ROOT);
    }

    @Override
    public void reset() {}
}
