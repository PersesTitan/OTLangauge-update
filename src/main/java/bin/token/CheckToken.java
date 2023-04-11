package bin.token;

import bin.Repository;
import bin.exception.VariableException;

import java.util.Set;

public interface CheckToken {
    static boolean isKlass(String klassType) {
        // static 에러 방지용 : Repository.createWorks != null
        return Repository.createWorks != null && Repository.createWorks.containsKey(klassType);
    }

    static void checkParamType(String[] types) {
        for (String type : types) checkParamType(type);
    }

    static void checkParamType(String type) {
        switch (type) {
            case KlassToken.KLASS, KlassToken.METHOD, KlassToken.STATIC_METHOD ->
                    throw VariableException.TYPE_ERROR.getThrow(type);
            default -> {
                if (!isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
            }
        }
    }

    static boolean haveCheckAccess(String line) {
        return line.substring(EditToken.getAccess(line)).contains(Token.ACCESS);
    }

    static boolean startWith(String line, char c) {
        return !line.isEmpty() && line.charAt(0) == c;
    }

    static boolean bothCheck(String line, char s, char e) {
        return startWith(line, s) && endWith(line, e);
    }

    static boolean isList(String line) {
        return line.startsWith(Token.LIST_S) && line.endsWith(Token.LIST_E);
    }

    static boolean isSet(String line) {
        return line.startsWith(Token.SET_S) && line.endsWith(Token.SET_E);
    }

    static boolean isMap(String line) {
        return line.startsWith(Token.MAP_S) && line.endsWith(Token.MAP_E);
    }

    static boolean isParams(String line) {
        return bothCheck(line, Token.PARAM_S, Token.PARAM_E) || HAVE_SET.contains(line.charAt(0));
    }

    static boolean endWith(String line, char c) {
        return !line.isEmpty() && line.charAt(line.length() - 1) == c;
    }

    Set<Character> HAVE_SET = Set.of(Token.PARAM_S, ' ', '\t', '\n', '\r', '\f');
    static boolean haveChars(String line) {
        return haveChars(line, HAVE_SET);
    }

    static boolean haveChars(String line, Set<Character> set) {
        for (char c : line.toCharArray()) {
            if (set.contains(c)) return true;
        }
        return false;
    }

    static boolean haveChars(String line, Character... chars) {
        return haveChars(line, Set.of(chars));
    }

    static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
