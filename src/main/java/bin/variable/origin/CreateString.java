package bin.variable.origin;

import bin.token.KlassToken;
import bin.variable.Types;
import work.CreateWork;

public class CreateString extends CreateWork<String> {
    public CreateString() {
        super(String.class, KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object createItem(Object... params) {
        return Types.toString(params[0]);
    }

    @Override
    public void reset() {}
}
