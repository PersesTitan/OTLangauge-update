package bin.variable.origin;

import bin.exception.VariableException;
import bin.token.KlassToken;
import bin.token.Token;
import work.CreateWork;

public class CreateBoolean extends CreateWork<Boolean> {
    public CreateBoolean() {
        super(Boolean.class, KlassToken.BOOL_VARIABLE, KlassToken.BOOL_VARIABLE);
    }

    @Override
    protected Object createItem(Object... params) {
        String value = params[0].toString();
        if (value.equals(Token.TRUE) || value.equals(Token.FALSE)) return value.equals(Token.TRUE);
        else throw VariableException.VALUE_ERROR.getThrow(value);
    }

    @Override
    public void reset() {}
}
