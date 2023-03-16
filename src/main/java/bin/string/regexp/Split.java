package bin.string.regexp;

import bin.token.KlassToken;
import bin.variable.Types;
import bin.variable.custom.CustomList;
import work.ReplaceWork;

import java.util.List;
import java.util.regex.Pattern;

public class Split extends ReplaceWork {
    public Split() {
        super(KlassToken.STRING_VARIABLE, false, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        String[] str = klassValue.toString().split(Pattern.quote(params[0].toString()));
        return new CustomList<>(Types.STRING, List.of(str));
    }

    @Override
    public void reset() {}
}
