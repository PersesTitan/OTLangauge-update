package bin.token;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public interface KlassToken {
    String METHOD = "ㅁㅅㅁ";
    String KLASS = "ㅋㅅㅋ";
    String FILE = "ㅍㅅㅍ";

    String PRINT = "ㅅㅁㅅ";
    String PRINTLN = "ㅆㅁㅆ";
    String PRINT_TAB = "ㅅㅁㅆ";
    String PRINT_SPACE = "ㅆㅁㅅ";

    String SYSTEM = "ㅆㅅㅆ";
    String QUIT = "ㄲㅌㄲ";

    // VARIABLE
    String INT_VARIABLE = "ㅇㅈㅇ";
    String LONG_VARIABLE = "ㅇㅉㅇ";
    String BOOL_VARIABLE = "ㅇㅂㅇ";
    String STRING_VARIABLE = "ㅇㅁㅇ";
    String CHARACTER_VARIABLE = "ㅇㄱㅇ";
    String FLOAT_VARIABLE = "ㅇㅅㅇ";
    String DOUBLE_VARIABLE = "ㅇㅆㅇ";

    // SET
    String SET_INTEGER = "ㄴㅈㄴ";
    String SET_LONG = "ㄴㅉㄴ";
    String SET_BOOLEAN = "ㄴㅂㄴ";
    String SET_STRING = "ㄴㅁㄴ";
    String SET_CHARACTER = "ㄴㄱㄴ";
    String SET_FLOAT = "ㄴㅅㄴ";
    String SET_DOUBLE = "ㄴㅆㄴ";

    // LIST
    String LIST_INTEGER = "ㄹㅈㄹ";
    String LIST_LONG = "ㄹㅉㄹ";
    String LIST_BOOLEAN = "ㄹㅂㄹ";
    String LIST_STRING = "ㄹㅁㄹ";
    String LIST_CHARACTER = "ㄹㄱㄹ";
    String LIST_FLOAT = "ㄹㅅㄹ";
    String LIST_DOUBLE = "ㄹㅆㄹ";

    // MAP
    String MAP_INTEGER = "ㅈㅈㅈ";
    String MAP_LONG = "ㅈㅉㅈ";
    String MAP_BOOLEAN = "ㅈㅂㅈ";
    String MAP_STRING = "ㅈㅁㅈ";
    String MAP_CHARACTER = "ㅈㄱㅈ";
    String MAP_FLOAT = "ㅈㅅㅈ";
    String MAP_DOUBLE = "ㅈㅆㅈ";

    AtomicReference<String> DEFAULT_KLASS = new AtomicReference<>(KlassToken.KLASS);

    Set<String> ORIGIN_KLASS = Set.of(INT_VARIABLE, LONG_VARIABLE, BOOL_VARIABLE, STRING_VARIABLE, CHARACTER_VARIABLE, FLOAT_VARIABLE, DOUBLE_VARIABLE);
    Set<String> SET_KLASS = Set.of(SET_INTEGER, SET_LONG, SET_BOOLEAN, SET_STRING, SET_CHARACTER, SET_FLOAT, SET_DOUBLE);
    Set<String> LIST_KLASS = Set.of(LIST_INTEGER, LIST_LONG, LIST_BOOLEAN, LIST_STRING, LIST_CHARACTER, LIST_FLOAT, LIST_DOUBLE);
    Set<String> MAP_KLASS = Set.of(MAP_INTEGER, MAP_LONG, MAP_BOOLEAN, MAP_STRING, MAP_CHARACTER, MAP_FLOAT, MAP_DOUBLE);
    Set<String> BASIC_KLASS = Set.of(
            INT_VARIABLE, LONG_VARIABLE, BOOL_VARIABLE, STRING_VARIABLE, CHARACTER_VARIABLE, FLOAT_VARIABLE, DOUBLE_VARIABLE,
            SET_INTEGER, SET_LONG, SET_BOOLEAN, SET_STRING, SET_CHARACTER, SET_FLOAT, SET_DOUBLE,
            LIST_INTEGER, LIST_LONG, LIST_BOOLEAN, LIST_STRING, LIST_CHARACTER, LIST_FLOAT, LIST_DOUBLE,
            MAP_INTEGER, MAP_LONG, MAP_BOOLEAN, MAP_STRING, MAP_CHARACTER, MAP_FLOAT, MAP_DOUBLE
    );
}
