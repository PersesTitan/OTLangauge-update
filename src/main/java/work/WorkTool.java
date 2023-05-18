package work;

import bin.apply.ReplaceType;
import bin.exception.FileException;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.Token;
import bin.variable.Types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

public interface WorkTool extends Serializable {
    int getSize();
    boolean isStatic();
    void reset();

    default void checkType(CreateWork<?> createWork, Object klassValue) {
        if (!createWork.check(klassValue))
            throw VariableException.VALUE_ERROR.getThrow(Types.toString(klassValue));
    }

    @Serial
    private void readObject(ObjectInputStream ois) {
        try {
            ois.defaultReadObject();
            reset();
        } catch (IOException | ClassNotFoundException e) {
            throw FileException.DO_NOT_INCLUDE.getThrow(e.getMessage());
        }
    }

    default String getString(CreateWork<?> createWork, int paramLen, String[] params) {
        StringBuilder builder = new StringBuilder(createWork.getKlassName());
        builder.append(' ');
        for (int i = 0; i < paramLen; i++) {
            builder.append(Token.PARAM_S);
            builder.append(params[i]);
            builder.append(Token.PARAM_E);
        }
        return builder.toString();
    }

    default Object getStartValues(CreateWork<?> createWork, boolean isStatic, Object klassValue) {
        if (isStatic) {
            if (klassValue != null) throw VariableException.ACCESS_ERROR.getThrow(klassValue);
            else return null;
        } else {
            this.checkType(createWork, klassValue);
            return klassValue;
        }
    }

    default Object[] getStartParams(CreateWork<?> createWork, int paramLen, String param, String[] params) {
        return switch(paramLen) {
            case 0 -> {
                if (param != null) throw MatchException.PARAM_COUNT_ERROR.getThrow(param);
                yield null;
            }
            case 1 -> {
                if (param == null) {
                    if (Types.STRING.originCheck(params[0])) yield new Object[] {""};
                    else {
                        if (createWork == null) throw MatchException.PARAM_COUNT_ERROR.getThrow(null);
                        else throw MatchException.PARAM_COUNT_ERROR.getThrow(
                                createWork.getKlassName() + " " + String.join(", ", params));
                    }
                } else {
                    if (CheckToken.bothCheck(param, Token.PARAM_S, Token.PARAM_E)) param = EditToken.bothCut(param);
                    else param = param.strip();
                    yield new Object[] {ReplaceType.replace(params[0], param)};
                }
            }
            default -> {
                String[] paramStr = EditToken.cutParams(paramLen, param);
                Object[] values = new Object[paramLen];
                for (int i = 0; i < paramLen; i++) values[i] = ReplaceType.replace(params[i], paramStr[i]);
                yield values;
            }
        };
    }
}
