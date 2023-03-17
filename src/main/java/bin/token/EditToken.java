package bin.token;

import bin.Repository;
import bin.exception.MatchException;
import bin.exception.VariableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public interface EditToken {
    static String bothCut(String line) {
        return line.substring(1, line.length() - 1);
    }

    static String bothCut(String line, int start, int end) {
        return line.substring(start, line.length() - end);
    }

    static String[] split(String line, String token) {
        int i = line.indexOf(token);
        if (i == -1) return new String[] {line, ""};
        else return new String[] {
                line.substring(0, i),
                line.substring(i + token.length())
        };
    }

    // [값1][값2][값3] => 값1, 값2, 값3
    static String[] cutParams(int paramCount, String line) {
        return switch (paramCount) {
            case 0 -> {
                if (line == null || line.isEmpty()) yield new String[0];
                else throw MatchException.PARAM_COUNT_ERROR.getThrow(line);
            }
            case 1 -> {
                if (CheckToken.startWith(line, Token.PARAM_S)) {
                    if (CheckToken.endWith(line, Token.PARAM_E)) yield new String[] {EditToken.bothCut(line)};
                    else throw MatchException.PARAM_MATCH_ERROR.getThrow(line);
                } else {
                    if (CheckToken.startWith(line, Token.PARAM_S)) yield new String[] {EditToken.bothCut(line)};
                    else yield new String[] {line.strip()};
                }
            }
            default -> {
                List<String> list = new ArrayList<>();
                Stack<Integer> stack = new Stack<>();
                char[] chars = line.toCharArray();
                int i = 0;
                for (char c : chars) {
                    switch (c) {
                        case Token.PARAM_S -> stack.add(i);
                        case Token.PARAM_E -> {
                            if (stack.isEmpty()) throw MatchException.PARAM_MATCH_ERROR.getThrow(line);
                            else if (stack.size() == 1) list.add(line.substring(stack.pop() + 1, i));
                            else stack.pop();
                        }
                        default -> {
                            if (stack.isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
                        }
                    }
                    i++;
                }
                if (!stack.isEmpty()) throw MatchException.PARAM_MATCH_ERROR.getThrow(line);
                if (paramCount != -1 && paramCount != list.size())
                    throw MatchException.PARAM_COUNT_ERROR.getThrow(list.toString());
                else yield list.toArray(new String[0]);
            }
        };
    }

    static int getAccess(String name) {
        if (!CheckToken.startWith(name, Token.ACCESS_C)) return 0;
        final int len = name.length();
        int count = 0;
        for (int i = 0; i < len; i++) {
            if (name.charAt(i) == Token.ACCESS_C) count++;
            else break;
        }
        return count;
    }
}
