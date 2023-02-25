package bin.variable.origin;

import bin.exception.VariableException;
import bin.token.KlassToken;
import work.CreateWork;

public class CreateLong extends CreateWork<Long> {
    public CreateLong() {
        super(Long.class, KlassToken.LONG_VARIABLE, KlassToken.LONG_VARIABLE);
    }

    @Override
    protected Object createItem(Object... params) {
        String value = params[0].toString();
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw VariableException.VALUE_ERROR.getThrow(value);
        }
    }

    @Override
    public void reset() {}
}
