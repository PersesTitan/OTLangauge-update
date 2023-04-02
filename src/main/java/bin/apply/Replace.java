package bin.apply;

import bin.apply.mode.DebugMode;
import bin.apply.tool.ApplyTool;
import bin.token.EditToken;
import bin.token.Token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replace extends ApplyTool {
//    public static Object replace(String line) {
//        return replace(line, true);
//    }

//    public static Object replace(String line, boolean isFirst) {
//        if (line.endsWith(Token.FIND_VARIABLE))
//            return repositoryArray.find(EditToken.bothCut(line, 0, Token.FIND_VARIABLE.length()));
//        else if (CheckToken.isSet(line)) return Types.toSet(line);
//        else if (CheckToken.isList(line)) return Types.toList(line);
//        else if (CheckToken.isMap(line)) return Types.toMap(line);
//        // [ , ' ' 를 가지고 있을때
//        else if (CheckToken.haveChars(line)) {
//            // 공백, 파라미터를 가지고 있을때
//            String[] tokens = getTokens(line);  // ㅋㅅㅋ~ㅁㅅㅁ[값1][값2] => [ㅇㅁㅇ~ㅁㅅㅁ, [값1][값2]]
//            if (CheckToken.haveCheckAccess(tokens[0]) || haveDefaultMethod(tokens[0])) {
//                // ㅋㅅㅋ~ㅁㅅㅁ, ㅆㅅㅆ~ㅋㅅㅋ
//                String[] km = getKM(tokens[0]);
//                String klassName = km[0], methodName = km[1];
//                if (CheckToken.isKlass(klassName)) {
//                    return replaceWorks.get(klassName, methodName).replace(null, tokens[1]);
//                } else if (repositoryArray.find(klassName)) {
//                    HpMap map = repositoryArray.getMap(klassName);
//                    String klassType = map.getKlassType();
//                    Object klassValue = map.get(klassName);
//                    return replaceWorks.get(klassType, methodName).replace(klassValue, tokens[1]);
//                } else return subReplace(line, isFirst);
//            } else if (repositoryArray.find(tokens[0])) {
//                // ㅋㅅㅋ + 파라미터
//                return repositoryArray.get(tokens[0]);
//            } else return subReplace(line, isFirst);
//        } else {
//            // 파라미터가 존재하지 않을때
//            // line = ㅋㅅㅋ~ㅁㅅㅁ
//            if (CheckToken.haveCheckAccess(line) || haveDefaultMethod(line)) {
//                // ㅋㅅㅋ~ㅁㅅㅁ, ㅆㅅㅆ~ㅋㅅㅋ
//                String[] km = getKM(line);
//                String klassName = km[0], methodName = km[1];
//                if (CheckToken.isKlass(klassName)) {
//                    return replaceWorks.get(klassName, methodName).replace(null, null);
//                } else if (repositoryArray.find(klassName)) {
//                    HpMap map = repositoryArray.getMap(klassName);
//                    String klassType = map.getKlassType();
//                    Object klassValue = map.get(klassName);
//                    return replaceWorks.get(klassType, methodName).replace(klassValue, null);
//                } else return subReplace(line, isFirst);
//            } else if (repositoryArray.find(line)) {
//                return repositoryArray.get(line);
//            } else return subReplace(line, isFirst);
//        }
//    }
//
//    private static boolean haveDefaultMethod(String methodName) {
//        return replaceWorks.contains(KlassToken.DEFAULT_KLASS.get(), methodName);
//    }
//
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
//                    String replaceToken = Types.toString(replace(value));
                    String replaceToken = ReplaceType.replace(value);
                    if (replaceToken != null) {
                        MATCH_D.reset(
                                line = line.replaceFirst(Pattern.quote(group),
                                value.equals(replaceToken) ? def : replaceToken)
                        );
                    }
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
//                String replaceToken = Types.toString(replace(value));
                String replaceToken = ReplaceType.replace(value);
                if (replaceToken != null && !value.equals(replaceToken)) {
                    MATCH_O.reset(line = line.replaceFirst(Pattern.quote(group), replaceToken));
                }
            } catch (Exception e) {
                if (DebugMode.isDevelopment()) e.printStackTrace();
            }
        }

        return line;
    }
}
