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

    static boolean startWith(String line, char c) {
        return !line.isEmpty() && line.charAt(0) == c;
    }

    static boolean endWith(String line, char c) {
        return !line.isEmpty() && line.charAt(line.length() - 1) == c;
    }

    // [값1][값2][값3] => 값1, 값2, 값3
    static String[] cutParams(int paramCount, String line) {
        return switch (paramCount) {
            case 0 -> {
                if (line == null) yield new String[0];
                else throw MatchException.PARAM_COUNT_ERROR.getThrow(line);
            }
            case 1 -> {
                if (startWith(line, Token.PARAM_S)) {
                    if (endWith(line, Token.PARAM_E)) yield new String[] {EditToken.bothCut(line)};
                    else throw MatchException.PARAM_MATCH_ERROR.getThrow(line);
                } else yield new String[] {line.strip()};
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
                if (paramCount != list.size()) throw MatchException.PARAM_COUNT_ERROR.getThrow(list.toString());
                yield list.toArray(new String[0]);
            }
        };
    }

    static String[] getTokens(String line) {
        String[] tokens = line.split("(?=\\s|\\" + Token.PARAM_S + ")", 2);
        if (tokens[0].isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        return tokens;
    }

    // 클래스, 메소드를 가져오는 로직
    static String[] getKM(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, Token.ACCESS);
        if (tokenizer.hasMoreTokens()) {
            return new String[] {tokenizer.nextToken(), tokenizer.hasMoreTokens() ? tokenizer.nextToken() : ""};
        } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
    }

    String CUT_PARAMS = Character.toString(Token.PARAM_E) + Token.PARAM_S;
    // token = [ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명1]
    static String[][] getParams(String line) {
        if (startWith(line, Token.PARAM_S) && endWith(line, Token.PARAM_E)) {
            String token = bothCut(line).strip();
            if (token.isEmpty()) return new String[0][0];
            else {
                int i = 0;
                StringTokenizer tokenizer = new StringTokenizer(token, CUT_PARAMS);
                String[][] params = new String[tokenizer.countTokens()][2];
                while (tokenizer.hasMoreTokens()) {
                    final String param = tokenizer.nextToken().strip();
                    final StringTokenizer st = new StringTokenizer(param);
                    if (st.countTokens() == 2) {
                        String klassType = st.nextToken();
                        // 타입이 유효한지 확인
                        Repository.checkParamType(klassType);
                        params[i][0] = klassType;
                        params[i++][1] = st.nextToken();
                    } else throw MatchException.GRAMMAR_ERROR.getThrow(param);
                }
                return params;
            }
        } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
    }
}
