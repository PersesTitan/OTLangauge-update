package bin.apply;

import bin.apply.calculator.bool.BoolCalculator;
import bin.apply.calculator.number.DoubleCalculator;
import bin.apply.calculator.number.FloatCalculator;
import bin.apply.calculator.number.IntegerCalculator;
import bin.apply.calculator.number.LongCalculator;
import bin.apply.mode.DebugMode;
import bin.apply.tool.ApplyTool;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.HpMap;
import bin.token.CheckToken;
import bin.token.KlassToken;
import bin.token.Token;
import bin.variable.Types;

import java.util.List;

import static bin.Repository.*;

public class ReplaceType extends ApplyTool {
    private static boolean haveNumberSing(String line) {
        for (String token : Token.numbers) {
            if (line.contains(token)) return true;
        }
        return false;
    }

    private static boolean haveBoolSing(String line) {
        return line.indexOf(Token.OR_C) >= 0
                || line.indexOf(Token.AND_C) >= 0
                || line.contains(Token.NOT)
                || Token.compares.stream().anyMatch(line::contains);
    }

    public static Object replace(String type, String line) {
        return switch (type) {
            case KlassToken.INT_VARIABLE -> {
                try {
                    yield Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    if (haveNumberSing(line)) yield IntegerCalculator.getInstance().calculator(line);
                    Object method = method(type, line);
                    if (method == null) {
                        if (subReplace(line, true) instanceof Integer i) yield i;
                        else throw VariableException.TYPE_ERROR.getThrow(line);
                    } else yield method;
                }
            }
            case KlassToken.LONG_VARIABLE -> {
                try {
                    yield Long.parseLong(line);
                } catch (NumberFormatException e) {
                    if (haveNumberSing(line)) yield LongCalculator.getInstance().calculator(line);
                    Object method = method(type, line);
                    if (method == null) {
                        if (subReplace(line, true) instanceof Long i) yield i;
                        else throw VariableException.TYPE_ERROR.getThrow(line);
                    } else yield method;
                }
            }
            case KlassToken.BOOL_VARIABLE -> {
                if (line.equals(Token.TRUE) || line.equals(Token.FALSE)) yield line.equals(Token.TRUE);
                else {
                    if (haveBoolSing(line)) yield BoolCalculator.getInstance().calculator(line);
                    Object method = method(type, line);
                    if (method == null) {
                        if (subReplace(line, true) instanceof Boolean bool) yield bool;
                        else throw VariableException.TYPE_ERROR.getThrow(line);
                    } else yield method;
                }
            }
            case KlassToken.STRING_VARIABLE -> {
                Object method = method(type, line);
                if (method == null) {
                    if (subReplace(line, true) instanceof String str) yield str;
                    else throw VariableException.TYPE_ERROR.getThrow(line);
                } else yield Types.toString(method);
            }
            case KlassToken.CHARACTER_VARIABLE -> {
                if (line.length() == 1) yield line.charAt(0);
                Object method = method(type, line);
                if (method == null) {
                    if (subReplace(line, true) instanceof Character c) yield c;
                    else throw VariableException.TYPE_ERROR.getThrow(line);
                } else yield method;
            }
            case KlassToken.FLOAT_VARIABLE -> {
                try {
                    yield Float.parseFloat(line);
                } catch (NumberFormatException e) {
                    if (haveNumberSing(line)) yield FloatCalculator.getInstance().calculator(line);
                    Object method = method(type, line);
                    if (method == null) {
                        if (subReplace(line, true) instanceof Float f) yield f;
                        else throw VariableException.TYPE_ERROR.getThrow(line);
                    } else yield method;
                }
            }
            case KlassToken.DOUBLE_VARIABLE -> {
                try {
                    yield Double.parseDouble(line);
                } catch (NumberFormatException e) {
                    if (haveNumberSing(line)) yield DoubleCalculator.getInstance().calculator(line);
                    Object method = method(type, line);
                    if (method == null) {
                        if (subReplace(line, true) instanceof Double d) yield d;
                        else throw VariableException.TYPE_ERROR.getThrow(line);
                    } else yield method;
                }
            }
            case KlassToken.SET_INTEGER -> setTool(Types.INTEGER, line);
            case KlassToken.SET_LONG -> setTool(Types.LONG, line);
            case KlassToken.SET_BOOLEAN -> setTool(Types.BOOLEAN, line);
            case KlassToken.SET_STRING -> setTool(Types.STRING, line);
            case KlassToken.SET_CHARACTER -> setTool(Types.CHARACTER, line);
            case KlassToken.SET_FLOAT -> setTool(Types.FLOAT, line);
            case KlassToken.SET_DOUBLE -> setTool(Types.DOUBLE, line);
            case KlassToken.LIST_INTEGER -> listTool(Types.INTEGER, line);
            case KlassToken.LIST_LONG -> listTool(Types.LONG, line);
            case KlassToken.LIST_BOOLEAN -> listTool(Types.BOOLEAN, line);
            case KlassToken.LIST_STRING -> listTool(Types.STRING, line);
            case KlassToken.LIST_CHARACTER -> listTool(Types.CHARACTER, line);
            case KlassToken.LIST_FLOAT -> listTool(Types.FLOAT, line);
            case KlassToken.LIST_DOUBLE -> listTool(Types.DOUBLE, line);
            case KlassToken.MAP_INTEGER -> mapTool(Types.INTEGER, line);
            case KlassToken.MAP_LONG -> mapTool(Types.LONG, line);
            case KlassToken.MAP_BOOLEAN -> mapTool(Types.BOOLEAN, line);
            case KlassToken.MAP_STRING -> mapTool(Types.STRING, line);
            case KlassToken.MAP_CHARACTER -> mapTool(Types.CHARACTER, line);
            case KlassToken.MAP_FLOAT -> mapTool(Types.FLOAT, line);
            case KlassToken.MAP_DOUBLE -> mapTool(Types.DOUBLE, line);
            default -> {
                if (CheckToken.isParams(line)) yield createWorks.get(type).create(line);
                else if (type.equals(line) || (line.startsWith(type + Token.PARAM_S)
                        && CheckToken.endWith(line, Token.PARAM_E)))
                    yield createWorks.get(type).create(line.substring(type.length()));
                Object method = method(type, line);
                if (method == null) throw MatchException.GRAMMAR_ERROR.getThrow(line);
                else yield method;
            }
        };
    }

    public static String replace(String line) {
        if (repositoryArray.find(line)) return Types.toString(repositoryArray.get(line));
        final String KLASS_NAME, METHOD_NAME, PARAMS;
        final Object KLASS_VALUE;
        String FIRST;
        if (CheckToken.haveChars(line)) {
            String[] tokens = getTokens(line);
            FIRST = tokens[0];
            PARAMS = tokens[1];
        } else {
            FIRST = line;
            PARAMS = null;
        }

        if (CheckToken.haveCheckAccess(FIRST) || replaceWorks.contains(KlassToken.DEFAULT_KLASS.get(), FIRST)) {
            String[] km = getKM(FIRST);
            METHOD_NAME = km[1];
            if (CheckToken.isKlass(km[0])) {
                KLASS_NAME = km[0];
                KLASS_VALUE = null;
            } else if (repositoryArray.find(km[0])) {
                HpMap map = repositoryArray.getMap(km[0]);
                KLASS_NAME = map.getKlassType();
                KLASS_VALUE = map.get(km[0]);
            } else return null;
        } else return null;
        return Types.toString(replaceWorks.get(KLASS_NAME, METHOD_NAME).replace(KLASS_VALUE, PARAMS));
    }

    private final static List<String> NUMBER_LIST = List.of(
            KlassToken.INT_VARIABLE, KlassToken.LONG_VARIABLE, KlassToken.FLOAT_VARIABLE, KlassToken.DOUBLE_VARIABLE
    );
    public static Object replaceNumber(String line) {
        for (String NUMBER_TYPE : NUMBER_LIST) {
            try {
                return replace(NUMBER_TYPE, line);
            } catch (Exception e) {
                if (DebugMode.isDevelopment()) e.printStackTrace();
            }
        }
        throw VariableException.VALUE_ERROR.getThrow(line);
    }

    public static double replaceDouble(String line) {
        Object value = replaceNumber(line);
        if (value instanceof Integer i) return (double) i;
        else if (value instanceof Long l) return (double) l;
        else if (value instanceof Float f) return (double) f;
        else if (value instanceof Double d) return d;
        throw VariableException.VALUE_ERROR.getThrow(line);
    }
}
