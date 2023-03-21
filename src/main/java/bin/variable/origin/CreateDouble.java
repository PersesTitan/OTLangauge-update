package bin.variable.origin;

import bin.apply.calculator.number.DoubleCalculator;
import bin.apply.calculator.number.FloatCalculator;
import bin.apply.calculator.number.NumberCalculator;
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
            if (NumberCalculator.haveNumber(value))
                return DoubleCalculator.getInstance().calculator(value);
            throw VariableException.VALUE_ERROR.getThrow(value);
        }
    }

    @Override
    public void reset() {}
}
