package bin.apply;

import bin.Repository;
import bin.Setting;
import bin.apply.km.klass.CreateKlass;
import bin.apply.km.klass.DefineKlass;
import bin.apply.km.method.DefineMethod;
import bin.apply.km.method.MethodReplace;
import bin.apply.km.method.MethodVoid;
import bin.apply.tool.ApplyTool;
import bin.apply.tool.StartTool;
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

import static bin.Repository.*;

public class Start extends ApplyTool {
    public static int subStart(String line, int start) {
        String[] tokens = cutSub(line);     // 변수명<<값 => [변수명, <<값]
        if (tokens.length == 1) Setting.runMessage(line, start);
        else subStart(tokens[0].strip(), tokens[1].strip(), start);
        return start + 1;
    }

    public static void subStart(String variable, String subToken, int start) {
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
        } else Setting.runMessage(variable + subToken, start);
    }

    /**
     * 변수명:값 <br>
     * 변수명 << 값 <br>
     * 변수명~메소드 값 <br>
     * 변수명~메소드[값] <br>
     */
    public static int start(String line, String path, int start, String repoKlass) {
        // 마지막이 '{'일때
        if (line.endsWith(Token.LOOP_S)) {
            // 중괄호 제거
            String[] tokens = getTokens(EditToken.bothCut(line, 0, 1).strip());
            return switch (tokens[0]) {
                case KlassToken.KLASS -> {
                    if (repoKlass == null || !repoKlass.equals(KlassToken.SYSTEM))
                        throw MatchException.CREATE_KLASS_ERROR.getThrow(line);
                    // ex) 클래스명[ㅇㅁㅇ 변수명] => [클래스명, [ㅇㅁㅇ 변수명]]
                    int position = tokens[1].indexOf(Token.PARAM_S);
                    String name  = tokens[1].substring(0, position).strip();    // 클래스명, 메소드명
                    String param = tokens[1].substring(position).strip();       // [ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2]
                    if (name.isEmpty() || param.isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
                    String[][] typeNames = StartTool.getParams(param);
                    DefineKlass defineKlass = new DefineKlass(name, path, start, typeNames);
                    createWorks.put(name, new CreateKlass(defineKlass));
                    yield defineKlass.getEnd() + 1;
                }
                case KlassToken.METHOD -> StartTool.createMethod(line, tokens[1], repoKlass, path, start, KlassToken.SYSTEM.equals(repoKlass));
                case KlassToken.STATIC_METHOD -> StartTool.createMethod(line, tokens[1], repoKlass, path, start, true);
                case Token.IF -> If.getInstance().start(codes.get(path), start, repoKlass);
                default -> {
                    // ' 값', '[값1][값2]'
                    String paramsValue = tokens.length != 1 && !tokens[1].isEmpty() ? tokens[1] : null;
                    String[] km = getKM(tokens[0]);
                    String klassName = km[0], methodName = km[1];
                    if (CheckToken.isKlass(klassName)) {
                        yield loopWorks.get(klassName, methodName).loop(null, paramsValue, path, start, repoKlass);
                    } else if (repositoryArray.find(klassName)) {
                        HpMap map = repositoryArray.getMap(klassName);
                        String klassType = map.getKlassType();
                        Object klassValue = map.get(klassName);
                        yield loopWorks.get(klassType, methodName).loop(klassValue, paramsValue, path, start, repoKlass);
                    } else yield subStart(line, start);
                }
            };
        } else {
            String[] tokens = getTokens(line);
            // ' 값', '[값1][값2]'
            if (tokens.length != 1 && !tokens[1].isEmpty()) {
                String params = tokens[1];
                // 파라미터가 존재할때 '['
                if (CheckToken.startWith(params, Token.PARAM_S)) {
                    String[] km = getKM(tokens[0]);
                    String klassName = km[0], methodName = km[1];
                    if (CheckToken.isKlass(klassName)) {
                        return startWorks.get(klassName, methodName).start(null, params, start);
                    } else if (repositoryArray.find(klassName)) {
                        HpMap map = repositoryArray.getMap(klassName);
                        String klassType = map.getKlassType();
                        Object klassValue = map.get(klassName);
                        return startWorks.get(klassType, methodName).start(klassValue, params, start);
                    } else return subStart(line, start);
                // 파라미터가 존재할때 ' '
                } else {
                    if (CheckToken.isKlass(tokens[0]) && params.contains(Token.PUT)) {
                        // 클래스 생성
                        String[] klassValues = EditToken.split(params.strip(), Token.PUT);
                        repositoryArray.create(tokens[0], klassValues[0].strip(), klassValues[1].strip());
                        return start + 1;
                    } else {
                        String[] km = getKM(tokens[0]);
                        String klassName = km[0], methodName = km[1];
                        if (CheckToken.isKlass(klassName)) {
                            return startWorks.get(klassName, methodName).start(null, params, start);
                        } else if (repositoryArray.find(klassName)) {
                            HpMap map = repositoryArray.getMap(klassName);
                            String klassType = map.getKlassType();
                            Object klassValue = map.get(klassName);
                            return startWorks.get(klassType, methodName).start(klassValue, params, start);
                        } else return subStart(line, start);
                    }
                }
            } else {
                // 파라미터가 존재하지 않을때
                String[] km = getKM(tokens[0]);
                String klassName = km[0], methodName = km[1];
                if (CheckToken.isKlass(klassName)) {
                    if (startWorks.contains(klassName, methodName)) {
                        return startWorks.get(klassName, methodName).start(null, null, start);
                    } else return subStart(line, start);
                } else if (repositoryArray.find(klassName)) {
                    HpMap map = repositoryArray.getMap(klassName);
                    String klassType = map.getKlassType();
                    Object klassValue = map.get(klassName);
                    if (startWorks.contains(klassType, methodName)) {
                        return startWorks.get(klassType, methodName).start(klassValue, null, start);
                    } else return subStart(line, start);
                } else return subStart(line, start);
            }
        }
    }

//    public static int createMethod(String line, String tokens,
//                                     String repoKlass, String path, int start, boolean isStatic) {
//        if (repoKlass == null) throw MatchException.CREATE_METHOD_ERROR.getThrow(line);
//        // ex) 메소드명[ㅇㅁㅇ 변수명] => [메소드명, [ㅇㅁㅇ 변수명]]
//        int position = tokens.indexOf(Token.PARAM_S);
//        String name  = tokens.substring(0, position).strip();    // 클래스명, 메소드명
//        String param = tokens.substring(position).strip();       // [ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명2]
//        if (name.isEmpty() || param.isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
//        DefineMethod method = new DefineMethod(repoKlass, name, path, start, getParams(param));
//        if (method.getReturnVarName() == null) startWorks.put(repoKlass, name, new MethodVoid(method, isStatic));
//        else replaceWorks.put(method.getReturnType(), repoKlass, name, new MethodReplace(method, isStatic));
//        return method.getEnd() + 1;
//    }

    private static int startItem(String line, String klassName, String methodName,
                                 String params, String path, int start, String repoKlass) {
        if (CheckToken.isKlass(klassName)) {
            if (klassName.equals(KlassToken.SYSTEM) && methodName.equals(Token.IF)) {
                return If.getInstance().start(codes.get(path), start, repoKlass);
            } else if (startWorks.contains(klassName, methodName)) {
                return startWorks.get(klassName, methodName).start(null, params, start);
            } else if (params == null) {
                methodName = EditToken.bothCut(methodName, 0, 1).strip();
                if (loopWorks.contains(klassName, methodName)) {
                    return loopWorks.get(klassName, methodName).loop(null, null, path, start, repoKlass);
                } else return subStart(line, start);
            } else {
                params = EditToken.bothCut(params, 0, 1).strip();
                if (loopWorks.contains(klassName, methodName)) {
                    params = params.isEmpty() ? null : params;
                    return loopWorks.get(klassName, methodName).loop(null, params, path, start, repoKlass);
                } else return subStart(line, start);
            }
        } else if (repositoryArray.find(klassName)) {
            HpMap map = repositoryArray.getMap(klassName);
            Object klassValue = map.get(klassName);
            klassName = map.getKlassType();
            if (startWorks.contains(klassName, methodName)) {
                return startWorks.get(klassName, methodName).start(klassValue, params, start);
            } else if (params == null) {
                String method = EditToken.bothCut(methodName, 0, 1).strip();
                if (loopWorks.contains(klassName, method)) {
                    return loopWorks.get(klassName, method).loop(klassValue, null, path, start, repoKlass);
                } else return subStart(line, start);
            } else {
                params = EditToken.bothCut(params, 0, 1).strip();
                if (loopWorks.contains(klassName, methodName)) {
                    params = params.isEmpty() ? null : params;
                    return loopWorks.get(klassName, methodName).loop(klassValue, params, path, start, repoKlass);
                } else return subStart(line, start);
            }
        } else return subStart(line, start);
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

//    private final static String CUT_PARAMS = Character.toString(Token.PARAM_E) + Token.PARAM_S;
//    // token = [ㅇㅁㅇ 변수명][ㅇㅈㅇ 변수명1]
//    private static String[][] getParams(String line) {
//        if (CheckToken.startWith(line, Token.PARAM_S) && CheckToken.endWith(line, Token.PARAM_E)) {
//            String token = EditToken.bothCut(line).strip();
//            if (token.isEmpty()) return new String[0][0];
//            else {
//                int i = 0;
//                StringTokenizer tokenizer = new StringTokenizer(token, CUT_PARAMS);
//                String[][] params = new String[tokenizer.countTokens()][2];
//                while (tokenizer.hasMoreTokens()) {
//                    final String param = tokenizer.nextToken().strip();
//                    final StringTokenizer st = new StringTokenizer(param);
//                    if (st.countTokens() == 2) {
//                        String klassType = st.nextToken();
//                        // 타입이 유효한지 확인
//                        CheckToken.checkParamType(klassType);
//                        params[i][0] = klassType;
//                        params[i++][1] = st.nextToken();
//                    } else throw MatchException.GRAMMAR_ERROR.getThrow(param);
//                }
//                return params;
//            }
//        } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
//    }
}
