package bin.variable.origin;

import bin.exception.VariableException;
import bin.token.KlassToken;
import work.CreateWork;

public class CreateCharacter extends CreateWork<Character> {
    public CreateCharacter() {
        super(Character.class, KlassToken.CHARACTER_VARIABLE, KlassToken.CHARACTER_VARIABLE);
    }

    @Override
    protected Object createItem(Object... params) {
        if (params[0] instanceof Integer i) return (char) ((int) i);
        else {
            String value = params[0].toString();
            if (value.length() == 1) return value.charAt(0);
            else {
                try {
                    return (char) Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw VariableException.VALUE_ERROR.getThrow(value);
                }
            }
        }
    }

    @Override
    public void reset() {}
}
