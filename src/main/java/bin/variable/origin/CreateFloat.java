package bin.variable.origin;

import bin.apply.calculator.number.FloatCalculator;
import bin.apply.calculator.number.NumberCalculator;
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
            if (NumberCalculator.haveNumber(value))
                return FloatCalculator.getInstance().calculator(value);
            throw VariableException.VALUE_ERROR.getThrow(value);
        }
    }

    @Override
    public void reset() {}
}
