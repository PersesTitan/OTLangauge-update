package bin.token;

import java.util.Set;

public interface Token {
    String VARIABLE = "[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]+(-?[0-9ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z])*";
    String REMARK = "#";
    String ACCESS = "~";
    String REPLACE_S = ":";
    String REPLACE_E = "_";
    String REPLACE_D = ";";
    String COMMA = ",";

    String CLEAR = "!";
    String PUT = ":";
    String ADD = "<<";

    String GET = ">>";
    String SIZE = "'";
    String CONTAINS = "?";

    String RETURN_TOKEN = "=>";
    String PUT_TOKEN = "<=";

    String LOOP_S = "{", LOOP_E = "}";
    char PARAM_S = '[', PARAM_E = ']';
    String SET_S = "(",  SET_E = ")";
    String LIST_S = "[", LIST_E = "]";
    String MAP_S = "{",  MAP_E = "}";

    char FOR = '^';

    String TRUE = "ㅇㅇ";
    String FALSE = "ㄴㄴ";
    String NOT = "ㅇㄴ";
    String OR = "ㄸ";
    String AND = "ㄲ";

    Set<String> NO_USE = Set.of(TRUE, FALSE, NOT, OR, AND);
}
