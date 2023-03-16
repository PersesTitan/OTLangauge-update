package bin.string.regexp;

import bin.token.KlassToken;
import bin.variable.Types;
import bin.variable.custom.CustomList;
import work.ReplaceWork;

import java.util.List;

public class SplitAll extends ReplaceWork {
    public SplitAll() {
        super(KlassToken.STRING_VARIABLE, false, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        String[] str = klassValue.toString().split(params[0].toString());
        return new CustomList<>(Types.STRING, List.of(str));
    }

    @Override
    public void reset() {}
}
