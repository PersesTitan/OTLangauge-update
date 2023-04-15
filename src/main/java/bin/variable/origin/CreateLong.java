package bin.variable.origin;

import bin.apply.calculator.number.FloatCalculator;
import bin.apply.calculator.number.LongCalculator;
import bin.apply.calculator.number.NumberCalculator;
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
            if (NumberCalculator.haveNumber(value))
                return LongCalculator.getInstance().calculator(value);
            throw VariableException.VALUE_ERROR.getThrow(value);
        }
    }

    @Override
    public void reset() {}
}
