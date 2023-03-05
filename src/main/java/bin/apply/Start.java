package bin.apply;

import bin.Repository;
import bin.Setting;
import bin.apply.km.klass.CreateKlass;
import bin.apply.km.klass.DefineKlass;
import bin.apply.km.method.DefineMethod;
import bin.apply.km.method.MethodReplace;
import bin.apply.km.method.MethodVoid;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.HpMap;
import bin.system.loop.If;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;
import bin.variable.custom.CustomList;
import bin.variable.custom.CustomMap;
import bin.variable.custom.CustomSet;

import java.util.StringTokenizer;

import static bin.Repository.*;

public class Start extends ApplyTool {
    private static int subStart(String line, int start) {
        String[] tokens = cutSub(line);     // 변수명<<값 => [변수명, <<값]
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
            } else Setting.runMessage(line);
        }

        return start + 1;
    }

    /**
     * 변수명:값 <br>
     * 변수명 << 값 <br>
     * 변수명~메소드 값 <br>
     * 변수명~메소드[값] <br>
     */
    public static int start(String line, String path, int start, String repoKlassName) {
        if (CheckToken.haveChars(line)) {
            // 공백, 파라미터를 가지고 있을때
            String[] tokens = getTokens(line);  // ㅋㅅㅋ~ㅁㅅㅁ[값1][값2]
            if (tokens.length == 1) throw MatchException.SYSTEM_ERROR.getThrow(line);
            String[] km = getKM(tokens[0]);     // [ㅋㅅㅋ, ㅁㅅㅁ]
            String klassName = km[0], methodName = km[1];
            if (CheckToken.startWith(tokens[1], Token.PARAM_S)) {
                // ?ㅅ?[ㅇㅇ] {
                if (Token.IF.equals(tokens[0])) {
                    return If.getInstance().start(codes.get(path), start);
                }
                // tokens[1] = [ㅇㅁㅇ 값]
                // klassName = ㅋㅅㅋ, methodName = (ㅁㅅㅁ, "")
                return startItem(line, klassName, methodName, tokens[1], start, path);
            } else {
                return switch (tokens[0]) {
                    case KlassToken.KLASS -> {
                        if (repoKlassName == null || !repoKlassName.equals(KlassToken.SYSTEM))
                            throw MatchException.CREATE_KLASS_ERROR.getThrow(line);
                        // ex) 클래스명[ㅇㅁㅇ 변수명] => [클래스명, [ㅇㅁㅇ 변수명]]
                        String[] klassTokens = cutKlassOrMethod(tokens[1]);
                        String name = klassTokens[0];
                        String[][] typeNames = getParams(klassTokens[1]);
                        DefineKlass defineKlass = new DefineKlass(name, path, start, typeNames);
                        createWorks.put(name, new CreateKlass(defineKlass));
                        yield defineKlass.getEnd() + 1;
                    }
                    case KlassToken.METHOD -> {
                        if (repoKlassName == null) throw MatchException.CREATE_METHOD_ERROR.getThrow(line);
                        // ex) 메소드명[ㅇㅁㅇ 변수명] => [메소드명, [ㅇㅁㅇ 변수명]]
                        String[] methodTokens = cutKlassOrMethod(tokens[1]);
                        String name = methodTokens[0];
                        String[][] typeNames = getParams(methodTokens[1]);
                        DefineMethod defineMethod = new DefineMethod(repoKlassName, name, path, start, typeNames);
                        if (defineMethod.getReturnVarName() == null) {  // void
                            startWorks.put(repoKlassName, name, new MethodVoid(defineMethod));
                        } else {                                        // replace
                            replaceWorks.put(repoKlassName, name, new MethodReplace(defineMethod));
                        }
                        yield defineMethod.getEnd() + 1;
                    }
                    case Token.IF -> If.getInstance().start(codes.get(path), start);
                    default -> startItem(line, klassName, methodName, tokens[1], start, path);
                };
            }
        } else {
            // 공백, 파라미터가 존재하지 않을때
            // ex1) '변수명' or '~변수명'
            // ex2) 'ㅁㅅㅁ' or 'ㅋㅅㅋ~ㅁㅅㅁ'
            // ex3) '변수명>>1'
            String[] km = getKM(line);
            String klassName = km[0], methodName = km[1];
            return startItem(line, klassName, methodName, null, start, path);
        }
    }

    /**
     * @param line <br>
     * ex1)  클래스명[ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2] { <br>
     * ex2)  메소드명[ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2] {
     * @return {클래스명, [ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2]}
     */
    private static String[] cutKlassOrMethod(String line) {
        // 괄호가 있는지 확인
        if (line.endsWith(Token.LOOP_S)) {
            int position = line.indexOf(Token.PARAM_S);
            String name  = line.substring(0, position).strip();                 // 클래스명, 메소드명
            String param = line.substring(position, line.length()-1).strip();   // [ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2]
            if (name.isEmpty() || param.isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
            return new String[] {name, param};
        } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
    }

    private static int startItem(String line, String klassName, String methodName,
                                 String param, int start, String path) {
        if (CheckToken.isKlass(klassName)) {
            return startSubItem(line, klassName, methodName, null, param, start, path);
        } else if (repositoryArray.find(klassName)) {
            HpMap map = repositoryArray.getMap(klassName);
            String klassType = map.getKlassType();
            Object klassValue = map.get(klassName);
            return startSubItem(line, klassType, methodName, klassValue, param, start, path);
        } else return subStart(line, start);
    }

    private static int startSubItem(String line, String klassType, String methodName,
                                    Object klassValue, String param, int start, String path) {
        if (startWorks.contains(klassType, methodName)) {
            return startWorks.get(klassType, methodName).start(klassValue, param, start);
        } else if (loopWorks.contains(klassType, methodName)) {
            if (CheckToken.endWith(param, Token.LOOP_SC)) {
                param = EditToken.bothCut(param, 0, 1).strip();
                return loopWorks.get(klassType, methodName).loop(klassValue, param, path, start);
            } else throw MatchException.ZONE_MATCH_ERROR.getThrow(param);
        } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
    }

    private final static String CUT_PARAMS = Character.toString(Token.PARAM_E) + Token.PARAM_S;
    // token = [ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명1]
    private static String[][] getParams(String line) {
        if (CheckToken.startWith(line, Token.PARAM_S) && CheckToken.endWith(line, Token.PARAM_E)) {
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
