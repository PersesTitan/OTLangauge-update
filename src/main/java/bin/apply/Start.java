package bin.apply;

import bin.Repository;
import bin.Setting;
import bin.apply.klass.CreateKlass;
import bin.apply.klass.DefineKlass;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.HpMap;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;
import bin.variable.custom.CustomList;
import bin.variable.custom.CustomMap;
import bin.variable.custom.CustomSet;

import java.util.HashSet;
import java.util.StringTokenizer;

import static bin.Repository.*;

public interface Start {
    private static void subStart(String line) {
        String[] tokens = line.split("(?!" + Token.VARIABLE + ")", 2);
        if (tokens.length == 1) Setting.runMessage(line);
        else {
            String variable = tokens[0].strip();
            String subToken = tokens[1].strip();
            if (subToken.equals(Token.CLEAR)) {
                Object value = Repository.repositoryArray.get(variable);
                if (value instanceof CustomList<?> list) list.clear();
                else if (value instanceof CustomSet<?> set) set.clear();
                else if (value instanceof CustomMap<?,?> map) map.clear();
                else throw VariableException.VALUE_ERROR.getThrow(variable);
            } else if (subToken.startsWith(Token.PUT)) {
                Repository.repositoryArray.update(variable, subToken.substring(1).strip());
            } else if (subToken.startsWith(Token.ADD)) {
                Object value = Repository.repositoryArray.get(variable);
                String addValue = subToken.substring(Token.ADD.length()).strip();
                if (value instanceof CustomList<?> list) list.add(addValue);
                else if (value instanceof CustomSet<?> set) set.add(addValue);
                else if (value instanceof CustomMap<?,?> map) map.put(addValue);
                else throw VariableException.VALUE_ERROR.getThrow(variable);
            }
        }
    }

    /**
     * 변수명:값 <br>
     * 변수명 << 값 <br>
     * 변수명~메소드 값 <br>
     * 변수명~메소드[값] <br>
     */

    static void start(String line, String path, int start) {
        String[] tokens = EditToken.getTokens(line);
        String[] km = EditToken.getKM(tokens[0]);
        String klassName = km[0], methodName = km[1];

        // 파라미터가 없을때
        if (tokens.length == 1) {
            // ㅋㅅㅋ~ㅁㅅㅁ (파마리터 X = null)
            startItem(line, klassName, methodName, null);
        } else if (EditToken.startWith(tokens[1], Token.PARAM_S)) {
            // klassName = ㅋㅅㅋ, methodName = (ㅁㅅㅁ, "")
            startItem(line, klassName, methodName, tokens[1]);
        } else {
            switch (tokens[0]) {
                case KlassToken.KLASS -> {
                    // ㅋㅅㅋ 클래스명[ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2] {
                    if (klassName.endsWith(Token.LOOP_S)) {
                        // token = 클래스명[ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2]
                        // 클래스명, [ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명1]
                        String[] klassTokens = klassName
                                .substring(0, klassName.length()-1)
                                .strip()
                                .split("(?=\\" + Token.PARAM_S + ")", 2);
                        if (klassTokens.length == 1) throw MatchException.GRAMMAR_ERROR.getThrow(klassName);
                        else {
                            // [[ㅇㅁㅇ, 변수명], [ㅇㅈㅇ, 변수명1]]
                            String[][] typeNames = EditToken.getParams(klassTokens[1]);
                            DefineKlass defineKlass = new DefineKlass(klassTokens[0], path, start, typeNames);
                            createWorks.put(klassTokens[0], new CreateKlass(defineKlass));
                        }
                    } else throw MatchException.GRAMMAR_ERROR.getThrow(klassName);
                }
                case KlassToken.METHOD -> {
                    // ㅁㅅㅁ 메소드명[ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2] {
                    if (klassName.endsWith(Token.LOOP_S)) {
                        // token = 메소드명[ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2]

                    }
                }
                default -> startItem(line, klassName, methodName, tokens[1]);
            }
        }
    }

    // [ㅇㅁㅇ 변수명][ㅇㅅㅇ 변수명1] => 2
    private static int count(String line) {
        final int len = line.length() - 1;
        final char[] texts = line.toCharArray();
        int count = 1;
        for (int i = 0; i < len; i++) {
            if (texts[i] == Token.PARAM_E && texts[i + 1] == Token.PARAM_S) {
                count++;
                i++;
            }
        }
        return count;
    }

    private static void startItem(String line, String klassName, String methodName, String param) {
        if (methodName.isEmpty()) {
            startWorks.get(KlassToken.KLASS, klassName).start(null, param);
        } else if (repositoryArray.find(klassName)) {
            HpMap map = repositoryArray.getMap(klassName);
            String klassType = map.getKlassType();
            Object klassValue = map.get(klassName);
            startWorks.get(klassType, methodName).start(klassValue, param);
        } else subStart(line);
    }
}
