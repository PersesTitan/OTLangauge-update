package bin.token;

import java.util.Set;

public interface Token {
    String VARIABLE = "[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]+(-?[0-9ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z])*";
    String KLASS_PATTERN = VARIABLE;
    String REMARK = "#";
    String REPLACE_S = ":";
    String REPLACE_E = "_";
    String REPLACE_D = ";";
    String COMMA = ",";

    String ACCESS = "~";
    char ACCESS_C = '~';

    String FIND_VARIABLE = "?!";
    String LOOP = "$";
    String LOOP_K = "ㅋ" + LOOP;
    String LOOP_V = "ㅂ" + LOOP;

    String CLEAR = "!";
    String PUT = ":";
    String ADD = "<<";

    String GET = ">>";
    String SIZE = "'";
    String CONTAINS = "?";
    String IS_EMPTY = "?";
    String SUM = "++";
    String MAX = "";
    String MIN = "";

    String RETURN_TOKEN = "=>";
    String PUT_TOKEN = "<=";

    String FOR_STR = "^";
    String LOOP_S = "{", LOOP_E = "}";
    String SET_S = "(",  SET_E = ")";
    String LIST_S = "[", LIST_E = "]";
    String MAP_S = "{",  MAP_E = "}";
    String MAP_CENTER = "=";

    String IF = "?ㅅ?";
    String ELSE_IF = "?ㅈ?";
    String ELSE = "?ㅉ?";

    String CALCULATOR_TOKEN = "ㅇ";
    String SUM_TOKEN        = "+";
    String MINUS_TOKEN      = "-";
    String MULTIPLY_TOKEN   = "*";
    String DIVIDE_TOKEN     = "/";
    String REMAIN_TOKEN     = "%";

    String SMALL        = "<";
    String BIG          = ">";
    String SAME         = "=";
    String SMALL_SAME   = "<=";
    String BIG_SAME     = ">=";

    char CALCULATOR_S = '(';
    char CALCULATOR_E = ')';

    String TRUE = "ㅇㅇ";
    String FALSE = "ㄴㄴ";
    String NOT = "ㅇㄴ";
    String OR = "ㄸ";
    String AND = "ㄲ";

    char PARAM_S = '[', PARAM_E = ']';
    String PARAM_SE = String.valueOf(PARAM_E) + PARAM_S;
    char FOR = FOR_STR.charAt(0);
    char LOOP_SC = LOOP_S.charAt(0);
    char LOOP_EC = LOOP_E.charAt(0);
    char OR_C = OR.charAt(0);
    char AND_C = AND.charAt(0);

    Set<String> NO_USE = Set.of(TRUE, FALSE, NOT, OR, AND);
    Set<String> numbers = Set.of(
            CALCULATOR_TOKEN.concat(SUM_TOKEN).concat(CALCULATOR_TOKEN),
            CALCULATOR_TOKEN.concat(MINUS_TOKEN).concat(CALCULATOR_TOKEN),
            CALCULATOR_TOKEN.concat(MULTIPLY_TOKEN).concat(CALCULATOR_TOKEN),
            CALCULATOR_TOKEN.concat(DIVIDE_TOKEN).concat(CALCULATOR_TOKEN),
            CALCULATOR_TOKEN.concat(REMAIN_TOKEN).concat(CALCULATOR_TOKEN)
    );
    Set<String> compares = Set.of(
            CALCULATOR_TOKEN.concat(SMALL).concat(CALCULATOR_TOKEN),
            CALCULATOR_TOKEN.concat(BIG).concat(CALCULATOR_TOKEN),
            CALCULATOR_TOKEN.concat(SAME).concat(CALCULATOR_TOKEN),
            CALCULATOR_TOKEN.concat(SMALL_SAME).concat(CALCULATOR_TOKEN),
            CALCULATOR_TOKEN.concat(BIG_SAME).concat(CALCULATOR_TOKEN)
    );
}
