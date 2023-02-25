package bin.variable.origin;

import bin.exception.VariableException;
import bin.token.KlassToken;
import work.CreateWork;

public class CreateDouble extends CreateWork<Double> {
    public CreateDouble() {
        super(Double.class, KlassToken.DOUBLE_VARIABLE, KlassToken.DOUBLE_VARIABLE);
    }

    @Override
    protected Object createItem(Object... params) {
        String value = params[0].toString();
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw VariableException.DEFINE_TYPE.getThrow(value);
        }
    }

    @Override
    public void reset() {}
}
