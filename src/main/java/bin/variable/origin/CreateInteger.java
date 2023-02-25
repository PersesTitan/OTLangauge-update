package bin.variable.origin;

import bin.exception.VariableException;
import bin.token.KlassToken;
import work.CreateWork;

public class CreateInteger extends CreateWork<Integer> {
    public CreateInteger() {
        super(Integer.class, KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE);
    }

    @Override
    protected Object createItem(Object... params) {
        if (params[0] instanceof Character c) return (int) c;
        else {
            String value = params[0].toString();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                if (value.length() == 1) return value.charAt(0);
                else throw VariableException.VALUE_ERROR.getThrow(value);
            }
        }
    }

    @Override
    public void reset() {}
}
