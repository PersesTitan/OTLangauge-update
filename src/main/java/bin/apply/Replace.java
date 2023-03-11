package bin.apply;

import bin.Repository;
import bin.apply.mode.DebugMode;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.Repository.*;

public class Replace extends ApplyTool {
    private static Object subReplace(String line, boolean isFirst) {
        String[] tokens = cutSub(line);
        String variable = tokens[0].strip();
        String subToken = tokens[1].strip();

        if (subToken.equals(Token.SIZE)) {
            Object value = repositoryArray.get(variable);
            if (value instanceof CustomSet<?> set) return set.size();
            else if (value instanceof CustomList<?> list) return list.size();
            else if (value instanceof CustomMap<?,?> map) return map.size();
            else if (value instanceof String str) return str.length();
            else throw VariableException.VALUE_ERROR.getThrow(variable);
        } else if (subToken.equals(Token.IS_EMPTY)) {
            Object value = repositoryArray.get(variable);
            if (value instanceof CustomSet<?> set) return set.isEmpty();
            else if (value instanceof CustomList<?> list) return list.isEmpty();
            else if (value instanceof CustomMap<?,?> map) return map.isEmpty();
            else if (value instanceof String str) return str.isEmpty();
            else throw VariableException.VALUE_ERROR.getThrow(variable);
        } else if (subToken.equals(Token.SUM)) {
            Object value = repositoryArray.get(variable);
            if (value instanceof CustomSet<?> set) return set.sum();
            else if (value instanceof CustomList<?> list) return list.sum();
            else if (value instanceof CustomMap<?,?> map) return map.sumValue();
            else throw VariableException.VALUE_ERROR.getThrow(variable);
        } else if (subToken.startsWith(Token.GET)) {
            // sub : 변수명>>값 => 값
            String sub = subToken.substring(Token.GET.length()).strip();
            Object value = repositoryArray.get(variable);
            if (value instanceof CustomSet<?> set) return set.get(sub);
            else if (value instanceof CustomList<?> list) return list.get(sub);
            else if (value instanceof CustomMap<?,?> map) return map.get(sub);
            else if (value instanceof String str) return str.charAt((int) Types.INTEGER.originCast(sub));
            else throw VariableException.VALUE_ERROR.getThrow(variable);
        } else if (subToken.startsWith(Token.CONTAINS)) {
            // sub : 변수명?값 => 값
            String sub = subToken.substring(Token.CONTAINS.length()).strip();
            Object value = repositoryArray.get(variable);
            if (value instanceof CustomSet<?> set) return set.contains(sub);
            else if (value instanceof CustomList<?> list) return list.contains(sub);
            else if (value instanceof CustomMap<?,?> map) return map.containsKey(sub);
            else if (value instanceof String str) return str.contains(sub);
            else throw VariableException.VALUE_ERROR.getThrow(variable);
        }

        return Types.toObject(line.strip(), isFirst);
    }

    public static Object replace(String line) {
        return replace(line, true);
    }

    public static Object replace(String line, boolean isFirst) {
        if (CheckToken.isSet(line)) return Types.toSet(line);
        else if (CheckToken.isList(line)) return Types.toList(line);
        else if (CheckToken.isMap(line)) return Types.toMap(line);
        // [ , ' ' 를 가지고 있을때
        else if (CheckToken.haveChars(line)) {
            // 공백, 파라미터를 가지고 있을때
            String[] tokens = getTokens(line);  // ㅋㅅㅋ~ㅁㅅㅁ[값1][값2] => [ㅇㅁㅇ~ㅁㅅㅁ, [값1][값2]]
            if (CheckToken.haveCheckAccess(tokens[0]) || haveDefaultMethod(tokens[0])) {
                // ㅋㅅㅋ~ㅁㅅㅁ, ㅆㅅㅆ~ㅋㅅㅋ
                String[] km = getKM(tokens[0]);
                String klassName = km[0], methodName = km[1];
                if (CheckToken.isKlass(klassName)) {
                    return replaceWorks.get(klassName, methodName).replace(null, tokens[1]);
                } else if (repositoryArray.find(klassName)) {
                    HpMap map = repositoryArray.getMap(klassName);
                    String klassType = map.getKlassType();
                    Object klassValue = map.get(klassName);
                    return replaceWorks.get(klassType, methodName).replace(klassValue, tokens[1]);
                } else return subReplace(line, isFirst);
            } else if (repositoryArray.find(tokens[0])) {
                // ㅋㅅㅋ + 파라미터
                return repositoryArray.get(tokens[0]);
            } else return subReplace(line, isFirst);
        } else {
            // 파라미터가 존재하지 않을때
            // line = ㅋㅅㅋ~ㅁㅅㅁ
            if (CheckToken.haveCheckAccess(line) || haveDefaultMethod(line)) {
                // ㅋㅅㅋ~ㅁㅅㅁ, ㅆㅅㅆ~ㅋㅅㅋ
                String[] km = getKM(line);
                String klassName = km[0], methodName = km[1];
                if (CheckToken.isKlass(klassName)) {
                    return replaceWorks.get(klassName, methodName).replace(null, null);
                } else if (repositoryArray.find(klassName)) {
                    HpMap map = repositoryArray.getMap(klassName);
                    String klassType = map.getKlassType();
                    Object klassValue = map.get(klassName);
                    return replaceWorks.get(klassType, methodName).replace(klassValue, null);
                } else return subReplace(line, isFirst);
            } else if (repositoryArray.find(line)) {
                return repositoryArray.get(line);
            } else return subReplace(line, isFirst);
        }
    }

    private static boolean haveDefaultMethod(String methodName) {
        return replaceWorks.contains(KlassToken.DEFAULT_KLASS.get(), methodName);
    }

    private static Object replace(String klassName, String methodName,
                                  Object klassValue, String paramLine) {
        return replaceWorks.get(klassName, methodName).replace(klassValue, paramLine);
    }

    // 값 치환 :값_
    public static boolean checkToken(String line) {
        return line.contains(Token.REPLACE_E) && line.contains(Token.REPLACE_S);
    }

    /**
     * :값_ or :값_기본값;
     */
    private final static String ALL = "[\\s\\S]";
    private final static Matcher MATCH_D = Pattern.compile(
            Token.REPLACE_S.concat("[^").concat(Token.REPLACE_S).concat("]").concat(ALL).concat("+")
                    .concat(Token.REPLACE_E).concat(ALL).concat("*").concat(Token.REPLACE_D)
    ).matcher("");
    private final static Matcher MATCH_O = Pattern.compile(
            Token.REPLACE_S.concat("[^").concat(Token.REPLACE_S).concat(Token.REPLACE_E).concat("]+")
                    .concat(Token.REPLACE_E)
    ).matcher("");
    public static String replaceToken(String line) {
        if (line.contains(Token.REPLACE_D)) {
            MATCH_D.reset(line);
            while (MATCH_D.find()) {
                String group = MATCH_D.group();
                // :값1_값2; => 값1_값2
                int i = group.indexOf(Token.REPLACE_E);
                String value = group.substring(1, i);
                String def = group.substring(i + Token.REPLACE_E.length(), group.length() - 1);
                try {
                    String replaceToken = Types.toString(replace(value));
                    MATCH_D.reset(line = line.replaceFirst(Pattern.quote(group),
                            value.equals(replaceToken) ? def : replaceToken));
                } catch (Exception e) {
                    if (DebugMode.isDevelopment()) e.printStackTrace();
                    MATCH_D.reset(line = line.replaceFirst(Pattern.quote(group), def));
                }
            }
        }

        MATCH_O.reset(line);
        while (MATCH_O.find()) {
            String group = MATCH_O.group();
            String value = EditToken.bothCut(group); // :값_ => 값
            try {
                String replaceToken = Types.toString(replace(value));
                if (!value.equals(replaceToken))
                    MATCH_O.reset(line = line.replaceFirst(Pattern.quote(group), replaceToken));
            } catch (Exception e) {
                if (DebugMode.isDevelopment()) e.printStackTrace();
            }
        }

        return line;
    }
}
