package bin.apply;

import bin.Repository;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.HpMap;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;
import bin.variable.Types;
import bin.variable.custom.CustomList;
import bin.variable.custom.CustomMap;
import bin.variable.custom.CustomSet;

import java.util.StringTokenizer;

import static bin.Repository.replaceWorks;
import static bin.Repository.repositoryArray;

public class ApplyTool {
    // 클래스, 메소드를 가져오는 로직
    // ㅋㅅㅋ~ㅁㅅㅁ => [ㅋㅅㅋ, ㅁㅅㅁ]
    public static String[] getKM(String line) {
        // ~~ㅋㅅㅋ~ㅁㅅㅁ => [~~ㅋㅅㅋ, ㅁㅅㅁ]
        if (CheckToken.startWith(line, Token.ACCESS_C)) {
            int access = EditToken.getAccess(line);     // ~~변수명
            StringTokenizer tokenizer = new StringTokenizer(line.substring(access), Token.ACCESS);
            if (tokenizer.hasMoreTokens()) {
                String klassName = tokenizer.nextToken();
                if (tokenizer.hasMoreTokens()) return new String[] {
                        Token.ACCESS.repeat(access).concat(klassName),
                        tokenizer.nextToken()
                };
            }
            throw MatchException.GRAMMAR_ERROR.getThrow(line);
        } else {
            StringTokenizer tokenizer = new StringTokenizer(line, Token.ACCESS);
            if (tokenizer.hasMoreTokens()) {
                String klassToken = tokenizer.nextToken();
                if (tokenizer.hasMoreTokens()) return new String[] {klassToken, tokenizer.nextToken()};
                else return new String[] {KlassToken.DEFAULT_KLASS.get(), klassToken};
            } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
        }
    }

    // ex1) ㅇㅁㅇ 값               => [ㅇㅁㅇ,  값]
    // ex2) ㅇㅁㅇ~ㅁㅅㅁ[값1][값2]    => [ㅇㅁㅇ~ㅁㅅㅁ, [값1][값2]]
    private static final String GET_TOKENS = "(?=\\s|\\" + Token.PARAM_S + ")";
    public static String[] getTokens(String line) {
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
    public static String[] cutSub(String line) {
        return line.split(CUT_SUB, 2);
    }

    static Object subReplace(String line, boolean isFirst) {
        String[] tokens = cutSub(line);
        String variable = tokens[0].strip();
        String subToken = tokens[1].strip();

        return switch (subToken) {
            case Token.FIND_VARIABLE -> repositoryArray.find(variable);
            case Token.SIZE -> {
                Object value = repositoryArray.get(variable);
                if (value instanceof CustomSet<?> set) yield set.size();
                else if (value instanceof CustomList<?> list) yield list.size();
                else if (value instanceof CustomMap<?,?> map) yield map.size();
                else if (value instanceof String str) yield str.length();
                else throw VariableException.VALUE_ERROR.getThrow(variable);
            }
            case Token.IS_EMPTY -> {
                Object value = repositoryArray.get(variable);
                if (value instanceof CustomSet<?> set) yield set.isEmpty();
                else if (value instanceof CustomList<?> list) yield list.isEmpty();
                else if (value instanceof CustomMap<?,?> map) yield map.isEmpty();
                else if (value instanceof String str) yield str.isEmpty();
                else throw VariableException.VALUE_ERROR.getThrow(variable);
            }
            case Token.SUM -> {
                Object value = repositoryArray.get(variable);
                if (value instanceof CustomSet<?> set) yield set.sum();
                else if (value instanceof CustomList<?> list) yield list.sum();
                else if (value instanceof CustomMap<?,?> map) yield map.sumValue();
                else throw VariableException.VALUE_ERROR.getThrow(variable);
            }
            default -> {
                if (subToken.startsWith(Token.GET)) {
                    // sub : 변수명>>값 => 값
                    String sub = subToken.substring(Token.GET.length()).strip();
                    Object value = repositoryArray.get(variable);
                    if (value instanceof CustomSet<?> set) yield set.get(sub);
                    else if (value instanceof CustomList<?> list) yield list.get(sub);
                    else if (value instanceof CustomMap<?,?> map) yield map.get(sub);
                    else if (value instanceof String str) yield str.charAt((int) Types.INTEGER.originCast(sub));
                    else throw VariableException.VALUE_ERROR.getThrow(variable);
                } else if (subToken.startsWith(Token.CONTAINS)) {
                    // sub : 변수명?값 => 값
                    String sub = subToken.substring(Token.CONTAINS.length()).strip();
                    Object value = repositoryArray.get(variable);
                    if (value instanceof CustomSet<?> set) yield set.contains(sub);
                    else if (value instanceof CustomList<?> list) yield list.contains(sub);
                    else if (value instanceof CustomMap<?,?> map) yield map.containsKey(sub);
                    else if (value instanceof String str) yield str.contains(sub);
                    else throw VariableException.VALUE_ERROR.getThrow(variable);
                }

                yield line;
//                yield Types.toObject(line.strip(), isFirst);
            }
        };
    }

    static Object method(String type, String line) {
        if (Repository.repositoryArray.find(type, line)) return Repository.repositoryArray.get(type, line);
        final String KLASS_NAME, METHOD_NAME, PARAMS;
        final Object KLASS_VALUE;
        String FIRST;
        if (CheckToken.haveChars(line)) {
            String[] tokens = getTokens(line);
            FIRST = tokens[0];
            PARAMS = tokens[1];
        } else {
            FIRST = line;
            PARAMS = null;
        }

        if (CheckToken.haveCheckAccess(FIRST) || replaceWorks.contains(type, KlassToken.DEFAULT_KLASS.get(), FIRST)) {
            String[] km = getKM(FIRST);
            METHOD_NAME = km[1];
            if (CheckToken.isKlass(km[0])) {
                KLASS_NAME = km[0];
                KLASS_VALUE = null;
            } else if (repositoryArray.find(km[0])) {
                HpMap map = repositoryArray.getMap(km[0]);
                KLASS_NAME = map.getKlassType();
                KLASS_VALUE = map.get(km[0]);
            } else return null;
        } else return null;

        return replaceWorks.get(type, KLASS_NAME, METHOD_NAME).replace(KLASS_VALUE, PARAMS);
    }

    // String -> Map
    static Object mapTool(Types types, String line) {
        if (CheckToken.isMap(line)) {
            CustomMap<?, ?> map = new CustomMap<>(Types.STRING, types);
            StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(line).strip(), ",");
            while (tokenizer.hasMoreTokens()) map.put(tokenizer.nextToken().strip());
            return map;
        } else {
            Object method = method(types.getMapType(), line);
            if (method == null) {
                if (subReplace(line, true) instanceof CustomMap<?,?> map
                        && map.getValueKlass().equals(types)) return map;
                else throw VariableException.TYPE_ERROR.getThrow(line);
            } else return method;
        }
    }

    // String -> Set
    static Object setTool(Types types, String line) {
        if (CheckToken.isSet(line)) {
            CustomSet<?> set = new CustomSet<>(types);
            StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(line).strip(), ",");
            while (tokenizer.hasMoreTokens()) set.add(tokenizer.nextToken().strip());
            return set;
        } else {
            Object method = method(types.getSetType(), line);
            if (method == null) {
                if (subReplace(line, true) instanceof CustomSet<?> set
                        && set.getTypes().equals(types)) return set;
                else throw VariableException.TYPE_ERROR.getThrow(line);
            } else return method;
        }
    }

    // String ->
    static Object listTool(Types types, String line) {
        if (CheckToken.isList(line)) {
            CustomList<?> list = new CustomList<>(types);
            StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(line).strip(), ",");
            while (tokenizer.hasMoreTokens()) list.add(tokenizer.nextToken().strip());
            return list;
        } else {
            Object method = method(types.getListType(), line);
            if (method == null) {
                if (subReplace(line, true) instanceof CustomList<?> list
                        && list.getTypes().equals(types)) return list;
                else throw VariableException.TYPE_ERROR.getThrow(line);
            } else return method;
        }
    }
}
