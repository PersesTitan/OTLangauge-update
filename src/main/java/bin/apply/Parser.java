package bin.apply;

import bin.Repository;
import bin.Setting;
import bin.exception.MatchException;
import bin.repository.HpMap;
import bin.token.EditToken;
import bin.token.Token;
import bin.token.KlassToken;

import java.util.Map;
import java.util.StringTokenizer;

public class Parser {
    public void parser(String line) {
        // ㅇㅁㅇ~ㅁㅅㅁ 값1
        String[] tokens = line.split("(?=\\s|\\" + Token.PARAM_S + ")", 2);
        if (tokens[0].isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        if (tokens.length == 1) {   // 파라미터가 없을때
            String[] token = tokens[0].split("(?!" + Token.VARIABLE + ")", 2);
            String subToken = token[1].strip();
            if (subToken.isEmpty()) {
                /**
                 * @TODO 파라미터가 없을때
                 */
            } else this.subToken(line, token[0], subToken);
        } else if (EditToken.startWith(tokens[1], Token.PARAM_S)) {
            if (EditToken.endWith(tokens[1], Token.PARAM_E)) {

            } else throw MatchException.GRAMMAR_ERROR.getThrow(tokens[1]);
        } else {
            // 값1 or [값1][값2]
            String paramValue = tokens[1].strip();
            String klassName = tokens[0];
            switch (klassName) {
                case KlassToken.KLASS -> {
                    // 클래스명[ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2] {
                    if (klassName.endsWith(Token.LOOP_S)) {

                    } else throw MatchException.GRAMMAR_ERROR.getThrow(klassName);
                }
                case KlassToken.METHOD -> {
                    // 메소드명[ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2] {

                }
                default -> {
                    // 클래스명일때 => 생성하기
                    if (Repository.isKlass(klassName)) {
                        // ㅇㅁㅇ 변수명:값
                        // paramValue = 변수명:값
                        if (paramValue.contains(Token.PUT)) {
                            String[] variable = paramValue.split(Token.PUT, 2);
                            Repository.repositoryArray.create(klassName, variable[0].strip(), variable[1].strip());
                        } else throw MatchException.GRAMMAR_ERROR.getThrow(paramValue);
                    } else {
                        StringTokenizer tokenizer = new StringTokenizer(klassName, Token.ACCESS);
                        if (tokenizer.hasMoreTokens()) {
                            String variableName = tokenizer.nextToken();
                            if (tokenizer.hasMoreTokens()) {
                                String methodName = tokenizer.nextToken();
                                HpMap map = Repository.repositoryArray.getMap(variableName);

                            } else {
                                // ㅅㅁㅅ 값1

                            }
                        } else throw MatchException.GRAMMAR_ERROR.getThrow(klassName);
                    }
                }
            }
        }
    }

    public void subToken(String line, String variableName, String subToken) {
        // 변수값 업데이트
        if (subToken.startsWith(Token.PUT)) {
            Repository.repositoryArray.update(variableName, subToken.substring(1).strip());
        }
        Setting.runMessage(line);
    }
}
