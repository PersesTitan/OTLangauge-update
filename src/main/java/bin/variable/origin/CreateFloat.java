package bin.variable.origin;

import bin.exception.VariableException;
import bin.token.KlassToken;
import work.CreateWork;

public class CreateFloat extends CreateWork<Float> {
    public CreateFloat() {
        super(Float.class, KlassToken.FLOAT_VARIABLE, KlassToken.FLOAT_VARIABLE);
    }

    @Override
    protected Object createItem(Object... params) {
        String value = params[0].toString();
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw VariableException.DEFINE_TYPE.getThrow(value);
        }
    }

    @Override
    public void reset() {}
}
