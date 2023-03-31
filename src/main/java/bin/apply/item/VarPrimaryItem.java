package bin.apply.item;

import bin.exception.VariableException;
import bin.token.CheckToken;

public record VarPrimaryItem(String type, String name, String primary) {
    public VarPrimaryItem(String type, String name, String primary) {
        this.type = type;
        this.name = name;
        this.primary = primary;
        if (!CheckToken.isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
    }
}
