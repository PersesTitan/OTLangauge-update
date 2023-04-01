package bin.apply.run;

import bin.apply.tool.ApplyTool;
import bin.apply.mode.DebugMode;
import bin.exception.FileException;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.Token;
import work.ResetWork;

import java.lang.reflect.InvocationTargetException;

public class Import {
    public void start(String line) {
        String kind = ApplyTool.getTokens(line)[1];
        if (CheckToken.startWith(kind, Token.PARAM_S)) {
            if (CheckToken.endWith(kind, Token.PARAM_E)) kind = EditToken.bothCut(kind);
            else throw FileException.ADD_FAIL_ERROR.getThrow(kind);
        } else kind = kind.strip();
        try {
            Class<?> klass = Class.forName(String.format("cos.%s.Reset", kind));
            ResetWork work = (ResetWork) klass.getConstructor().newInstance();
            work.reset();
        } catch (ClassNotFoundException | InvocationTargetException |
                 InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            if (DebugMode.isDevelopment()) e.printStackTrace();
            throw FileException.ADD_FAIL_ERROR.getThrow(kind);
        }
    }
}
