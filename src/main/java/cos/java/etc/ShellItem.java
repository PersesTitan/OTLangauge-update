package cos.java.etc;

import bin.Repository;
import bin.token.KlassToken;

public class ShellItem {
    public static String otlToJava(String token) {
        return switch (token) {
            case KlassToken.INT_VARIABLE -> "int";
            case KlassToken.LONG_VARIABLE -> "long";
            case KlassToken.BOOL_VARIABLE -> "boolean";
            case KlassToken.STRING_VARIABLE -> "String";
            case KlassToken.CHARACTER_VARIABLE -> "char";
            case KlassToken.FLOAT_VARIABLE -> "float";
            case KlassToken.DOUBLE_VARIABLE -> "double";
            case KlassToken.SET_INTEGER -> "CustomSet<Integer>";
            case KlassToken.SET_LONG -> "CustomSet<Long>";
            case KlassToken.SET_BOOLEAN -> "CustomSet<Boolean>";
            case KlassToken.SET_STRING -> "CustomSet<String>";
            case KlassToken.SET_CHARACTER -> "CustomSet<Character>";
            case KlassToken.SET_FLOAT -> "CustomSet<Float>";
            case KlassToken.SET_DOUBLE -> "CustomSet<Double>";
            case KlassToken.LIST_INTEGER -> "CustomList<Integer>";
            case KlassToken.LIST_LONG -> "CustomList<Long>";
            case KlassToken.LIST_BOOLEAN -> "CustomList<Boolean>";
            case KlassToken.LIST_STRING -> "CustomList<String>";
            case KlassToken.LIST_CHARACTER -> "CustomList<Character>";
            case KlassToken.LIST_FLOAT -> "CustomList<Float>";
            case KlassToken.LIST_DOUBLE -> "CustomList<Double>";
            case KlassToken.MAP_INTEGER -> "CustomMap<String, Integer>";
            case KlassToken.MAP_LONG -> "CustomMap<String, Long>";
            case KlassToken.MAP_BOOLEAN -> "CustomMap<String, Boolean>";
            case KlassToken.MAP_STRING -> "CustomMap<String, String>";
            case KlassToken.MAP_CHARACTER -> "CustomMap<String, Character>";
            case KlassToken.MAP_FLOAT -> "CustomMap<String, Float>";
            case KlassToken.MAP_DOUBLE -> "CustomMap<String, Double>";
            default -> Repository.createWorks.get(token).getKlass().getName();
        };
    }
}
