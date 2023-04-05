package bin.string.tocase;

import bin.token.KlassToken;
import work.ReplaceWork;

import java.util.Locale;

public class ToLowerCase extends ReplaceWork {
    public ToLowerCase() {
        super(
                KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE, false
        );
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return klassValue.toString().toLowerCase(Locale.ROOT);
    }

    @Override
    public void reset() {}
}
