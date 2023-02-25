package bin;

import bin.exception.VariableException;
import bin.repository.AccessList;
import bin.repository.WorkMap;
import bin.token.KlassToken;
import bin.variable.Types;
import work.CreateWork;
import work.ReplaceWork;
import work.StartWork;

import java.util.HashMap;
import java.util.Map;

public interface Repository {
    Map<String, Map<Integer, String>> codes = new HashMap<>();

    AccessList repositoryArray = new AccessList();

    WorkMap<ReplaceWork> replaceWorks = new WorkMap<>();
    WorkMap<StartWork> startWorks = new WorkMap<>();
    Map<String, CreateWork<?>> createWorks = new HashMap<>() {{
        for (Types types : Types.values()) {
            put(types.getOriginType(), types.getCreateWork());
            put(types.getSetType(), types.getSetWork());
            put(types.getListType(), types.getListWork());
            put(types.getMapType(), types.getMapWork());
        }
    }};

    static boolean isKlass(String klassType) {
        return createWorks.containsKey(klassType);
    }

    static void checkParamType(String[] types) {
        for (String type : types) checkParamType(type);
    }

    static void checkParamType(String type) {
        switch (type) {
            case KlassToken.KLASS, KlassToken.METHOD -> throw VariableException.TYPE_ERROR.getThrow(type);
            default -> {
                if (!isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
            }
        }
    }
}
