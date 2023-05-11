package cos.poison.controller;

import bin.exception.VariableException;
import bin.token.CheckToken;
import bin.token.KlassToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum URLItem {
    INT("([+\\-])?\\d+"),
    FLOAT(INT.getText() + "\\.\\d+"),
    CHAR("[^/]"),
    STRING(CHAR.getText() + "*"),
    BOOL("(true|false|ㅇㅇ|ㄴㄴ)");

    private final String text;

    public static URLItem getType(String type) {
        return switch (type) {
            case KlassToken.INT_VARIABLE, KlassToken.LONG_VARIABLE -> INT;
            case KlassToken.FLOAT_VARIABLE, KlassToken.DOUBLE_VARIABLE -> FLOAT;
            case KlassToken.CHARACTER_VARIABLE -> CHAR;
            case KlassToken.STRING_VARIABLE -> STRING;
            case KlassToken.BOOL_VARIABLE -> BOOL;
            default -> {
                if (CheckToken.isKlass(type)) throw VariableException.TYPE_ERROR.getThrow(type);
                else throw VariableException.NO_DEFINE_TYPE.getThrow(type);
            }
        };
    }
}
