package bin.apply.tool;

import bin.apply.km.method.DefineMethod;
import bin.apply.km.method.MethodReplace;
import bin.apply.km.method.MethodVoid;
import bin.exception.MatchException;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.Token;

import java.util.StringTokenizer;

import static bin.Repository.replaceWorks;
import static bin.Repository.startWorks;

public interface StartTool {
    static int createMethod(String line, String tokens, String repoKlass, String path, int start, boolean isStatic) {
        if (repoKlass == null) throw MatchException.CREATE_METHOD_ERROR.getThrow(line);
        // ex) 메소드명[ㅇㅁㅇ 변수명] => [메소드명, [ㅇㅁㅇ 변수명]]
        int position = tokens.indexOf(Token.PARAM_S);
        String name  = tokens.substring(0, position).strip();    // 클래스명, 메소드명
        String param = tokens.substring(position).strip();       // [ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2]
        if (name.isEmpty() || param.isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        DefineMethod method = new DefineMethod(repoKlass, name, path, start, getParams(param));
        if (method.getReturnVarName() == null) startWorks.put(repoKlass, name, new MethodVoid(method, isStatic));
        else replaceWorks.put(method.getReturnType(), repoKlass, name, new MethodReplace(method, isStatic));
        return method.getEnd() + 1;
    }

    String CUT_PARAMS = Character.toString(Token.PARAM_E) + Token.PARAM_S;
    static String[][] getParams(String line) {
        if (CheckToken.isParams(line)) {
            String token = EditToken.bothCut(line).strip();
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
                        CheckToken.checkParamType(klassType);
                        params[i][0] = klassType;
                        params[i++][1] = st.nextToken();
                    } else throw MatchException.GRAMMAR_ERROR.getThrow(param);
                }
                return params;
            }
        } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
    }
}
