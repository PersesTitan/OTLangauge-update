package bin.apply.line;

import bin.apply.tool.ApplyTool;
import bin.repository.HpMap;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.Token;

import static bin.Repository.*;

public interface LineTool {
    void startItem(int start);
    default int start(int start) {
        this.startItem(start);
        return start + 1;
    }

    /**
     *  CREATE : 클래스명 변수명:값
     *  SYSTEM METHOD : 메소드명, ㅆㅅㅆ~메소드명
     *  STATIC METHOD : 클래스명~메소드명
     *  ORIGIN METHOD : 변수명~메소드명
     *  CLASS : ㅋㅅㅋ 클래스명[] {
     *  METHOD : ㅁㅅㅁ 메소드명[] {
     */
    static LineTool create(String line) {
        if (line.endsWith(Token.LOOP_S)) {
            return null;
        } else {
            String[] tokens = ApplyTool.getTokens(line);
            if (tokens.length != 1 && !tokens[1].isEmpty()) {
                String params = tokens[1];
                if (CheckToken.startWith(params, Token.PARAM_S)) {
                    String[] km = ApplyTool.getKM(tokens[0]);
                    return getLineTool(line, km[0], km[1], params);
                } else {
                    if (CheckToken.isKlass(tokens[0]) && params.contains(Token.PUT)) {
                        String[] klassValues = EditToken.split(params.strip(), Token.PUT);
                        return new LineCreateItem(tokens[0], klassValues[0].strip(), klassValues[1].strip());
                    } else {
                        String[] km = ApplyTool.getKM(tokens[0]);
                        return getLineTool(line, km[0], km[1], params);
                    }
                }
            } else {
                // 파라미터가 존재하지 않을때
                String[] km = ApplyTool.getKM(tokens[0]);
                return getLineTool(line, km[0], km[1], null);
            }
        }
    }

    private static LineTool getLineTool(String line, String klassName, String methodName, String params) {
        if (CheckToken.isKlass(klassName)) return new LineStaticItem(klassName, methodName, params);
        else if (repositoryArray.find(klassName)) {
            HpMap map = repositoryArray.getMap(klassName);
            return new LineStartItem(map.getKlassType(), methodName, params, map, klassName);
        } else return new LineSubStart(line);
    }
}
