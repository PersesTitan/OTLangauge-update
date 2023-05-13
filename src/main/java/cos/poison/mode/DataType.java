package cos.poison.mode;

import bin.token.CheckToken;
import cos.poison.etc.PoisonException;

import java.util.Locale;

public enum DataType {
    JSON, URL;

    public static boolean isType(String dataType) {
        try {
            DataType.valueOf(dataType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static DataType getType(String query) {
        if (CheckToken.bothCheck(query, '{', '}')) return JSON;
        else return URL;
    }

    public static DataType casting(String value) {
        try {
            return DataType.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw PoisonException.IN_DATA_TYPE_ERROR.getThrow(value);
        }
    }
}
