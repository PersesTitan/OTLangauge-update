package bin.apply.item;

import bin.exception.VariableException;
import bin.token.CheckToken;

public record VariableItem(String type, String name) {
    public VariableItem(String type, String name) {
        this.type = type;
        this.name = name;
        if (!CheckToken.isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
    }
}
