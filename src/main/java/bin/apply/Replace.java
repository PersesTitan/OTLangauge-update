package bin.apply;

import bin.Repository;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.HpMap;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;
import bin.variable.Types;
import bin.variable.custom.CustomList;
import bin.variable.custom.CustomMap;
import bin.variable.custom.CustomSet;

import static bin.Repository.*;

public interface Replace {
    private static Object subReplace(String line) {
        String[] tokens = line.split("(?!" + Token.VARIABLE + ")", 2);
        String variable = tokens[0].strip();
        String subToken = tokens[1].strip();

        if (subToken.equals(Token.SIZE)) {
            Object value = repositoryArray.get(variable);
            if (value instanceof CustomSet<?> set) return set.size();
            else if (value instanceof CustomList<?> list) return list.size();
            else if (value instanceof CustomMap<?,?> map) return map.size();
            else if (value instanceof String str) return str.length();
            else throw VariableException.VALUE_ERROR.getThrow(variable);
        } else if (subToken.startsWith(Token.GET)) {
            String sub = subToken.substring(Token.GET.length()).strip();
            Object value = repositoryArray.get(variable);
            if (value instanceof CustomSet<?> set) return set.get(sub);
            else if (value instanceof CustomList<?> list) return list.get(sub);
            else if (value instanceof CustomMap<?,?> map) return map.get(sub);
            else if (value instanceof String str) return str.charAt((int) Types.INTEGER.originCast(sub));
            else throw VariableException.VALUE_ERROR.getThrow(variable);
        } else if (subToken.startsWith(Token.CONTAINS)) {
            String sub = subToken.substring(Token.CONTAINS.length()).strip();
            Object value = repositoryArray.get(variable);
            if (value instanceof CustomSet<?> set) return set.contains(sub);
            else if (value instanceof CustomList<?> list) return list.contains(sub);
            else if (value instanceof CustomMap<?,?> map) return map.containsKey(sub);
            else throw VariableException.VALUE_ERROR.getThrow(variable);
        }

        throw MatchException.GRAMMAR_ERROR.getThrow(line);
    }

    static Object replace(String line) {
        String[] tokens = line.split("(?=\\s|\\" + Token.PARAM_S + ")", 2);
        String[] km = EditToken.getKM(tokens[0]);
        String klassName = km[0], methodName = km[1];

        if (tokens.length == 1) {
            // ex) ㅋㅅㅋ~ㅁㅅㅁ
            // 파라미터가 존재하지 않는 메소드 일때
            if (methodName.isEmpty()) {
                return replaceWorks.get(KlassToken.SYSTEM, klassName).replace(null, null);
            } else if (repositoryArray.find(klassName)){
                HpMap map = repositoryArray.getMap(klassName);
                String klassType = map.getKlassType();
                Object klassValue = map.get(klassName);
                return replaceWorks.get(klassType, methodName).replace(klassValue, null);
            } else return subReplace(line);
        } else {
            // ex) ㅋㅅㅋ~ㅁㅅㅁ 값1
            // 값1, [값1][값2]
            if (methodName.isEmpty()) {
                return replaceWorks.get(KlassToken.SYSTEM, klassName).replace(null, tokens[1]);
            } else if (repositoryArray.find(klassName)) {
                HpMap map = repositoryArray.getMap(klassName);
                String klassType = map.getKlassType();
                Object klassValue = map.get(klassName);
                return replaceWorks.get(klassType, methodName).replace(klassValue, tokens[1]);
            } else return subReplace(line);
        }
    }
}
