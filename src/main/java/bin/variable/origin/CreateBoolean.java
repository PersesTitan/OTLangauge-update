package bin.variable.origin;

import bin.apply.calculator.bool.BoolCalculator;
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
        else if (this.haveBoolCalculator(value)) return BoolCalculator.getInstance().calculator(value);
        else throw VariableException.VALUE_ERROR.getThrow(value);
    }

    private boolean haveBoolCalculator(String value) {
        return value.indexOf(Token.OR_C) >= 0
                || value.indexOf(Token.AND_C) >= 0
                || value.contains(Token.NOT)
                || Token.compares.stream().anyMatch(value::contains);
    }

    @Override
    public void reset() {}
}
