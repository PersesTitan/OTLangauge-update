package bin.apply;

import bin.exception.MatchException;
import bin.repository.AccessList;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;

import java.util.StringTokenizer;

public class ApplyTool {
    // 클래스, 메소드를 가져오는 로직
    // ㅋㅅㅋ~ㅁㅅㅁ => [ㅋㅅㅋ, ㅁㅅㅁ]
    static String[] getKM(String line) {
        // ~~ㅋㅅㅋ~ㅁㅅㅁ => [~~ㅋㅅㅋ, ㅁㅅㅁ]
        if (CheckToken.startWith(line, Token.ACCESS_C)) {
            int access = EditToken.getAccess(line);     // ~~변수명
            StringTokenizer tokenizer = new StringTokenizer(line.substring(access), Token.ACCESS);
            if (tokenizer.hasMoreTokens()) {
                String klassToken = tokenizer.nextToken();
                if (tokenizer.hasMoreTokens()) {
                    return new String[] {
                            Token.ACCESS.repeat(access).concat(klassToken),
                            tokenizer.nextToken()
                    };
                } else {
                    return new String[] {
                            KlassToken.DEFAULT_KLASS.get(),
                            Token.ACCESS.repeat(access).concat(klassToken)
                    };
                }
            } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
        } else {
            StringTokenizer tokenizer = new StringTokenizer(line, Token.ACCESS);
            if (tokenizer.hasMoreTokens()) {
                String klassToken = tokenizer.nextToken();
                /**
                 * @TODO
                 * klassToken 이 klass 이름일때
                 */
                if (tokenizer.hasMoreTokens()) return new String[] {klassToken, tokenizer.nextToken()};
                else return new String[] {KlassToken.DEFAULT_KLASS.get(), klassToken};
            } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
        }
    }

    // ex1) ㅇㅁㅇ 값               => [ㅇㅁㅇ,  값]
    // ex2) ㅇㅁㅇ~ㅁㅅㅁ[값1][값2]    => [ㅇㅁㅇ~ㅁㅅㅁ, [값1][값2]]
    private static final String GET_TOKENS = "(?=\\s|\\" + Token.PARAM_S + ")";
    static String[] getTokens(String line) {
        String[] tokens = line.split(GET_TOKENS, 2);
        if (tokens[0].isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        return tokens;
    }

    // ex) 클래스명[ㅇㅁㅇ 변수명] => [클래스명, [ㅇㅁㅇ 변수명]]
    // ex) 메소드명[ㅇㅁㅇ 변수명] => [메소드명, [ㅇㅁㅇ 변수명]]
    private static final String CUT_KLASS_METHOD = "(?=\\" + Token.PARAM_S + ")";
    static String[] cutKlassMethod(String line) {
        return line.substring(0, line.length()-1).strip().split(CUT_KLASS_METHOD, 2);
    }

    private static final String CUT_SUB = "(?!" + Token.VARIABLE + ")";
    static String[] cutSub(String line) {
        return line.split(CUT_SUB, 2);
    }
}
