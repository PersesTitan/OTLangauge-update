package bin.repository;

import bin.exception.VariableException;
import bin.token.CheckToken;
import bin.token.Token;
import work.ReplaceWork;

import java.util.HashMap;

public class ReplaceMap extends HashMap<String, WorkMap<ReplaceWork>> {
    // type = return, klass = klassType, method = methodName
    public void put(String type, String klass, String method, ReplaceWork work) {
        if (!CheckToken.isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
        if (this.isHave(klass, method))
            throw VariableException.DEFINE_METHOD.getThrow(klass + Token.ACCESS + method);
        if (super.containsKey(type)) super.get(type).put(klass, method, work);
        else super.put(type, new WorkMap<>(klass, method, work));
    }

    public ReplaceWork get(String type, String klass, String method) {
        if (!CheckToken.isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
        if (this.isHave(klass, method)) {
            WorkMap<ReplaceWork> map;
            if (super.containsKey(type) && (map = super.get(type)).contains(klass, method)) {
                return map.get(klass, method);
            } else throw VariableException.TYPE_ERROR.getThrow(klass + Token.ACCESS + method);
        } else throw VariableException.NO_DEFINE_METHOD.getThrow(klass + Token.ACCESS + method);
    }

    public ReplaceWork get(String klass, String method) {
        for (WorkMap<ReplaceWork> map : super.values()) {
            if (map.contains(klass, method)) return map.get(klass, method);
        }
        throw VariableException.NO_DEFINE_METHOD.getThrow(klass + Token.ACCESS + method);
    }

    public boolean contains(String type, String klass, String method) {
        return super.containsKey(type) && super.get(type).contains(klass, method);
    }

    public boolean contains(String klass, String method) {
        return this.isHave(klass, method);
    }

    private boolean isHave(String klass, String method) {
        for (WorkMap<ReplaceWork> map : super.values()) {
            if (map.contains(klass, method)) return true;
        }
        return false;
    }
}
