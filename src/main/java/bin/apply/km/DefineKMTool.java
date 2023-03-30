package bin.apply.km;

import bin.exception.MatchException;
import bin.repository.TypeMap;

public interface DefineKMTool {
    default void setParam(TypeMap repository, Object[] values,
                          String[] paramType, String[] paramName) {
        int len = paramType.length;
        if (len == values.length) {
            for (int i = 0; i < len; i++) repository.create(paramType[i], paramName[i], values[i]);
        } else throw MatchException.PARAM_MATCH_ERROR.getThrow(Integer.toString(values.length));
    }
}
