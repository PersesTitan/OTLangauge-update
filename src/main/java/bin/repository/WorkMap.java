package bin.repository;

import bin.exception.VariableException;
import bin.token.CheckToken;
import bin.token.KlassToken;
import bin.token.Token;
import lombok.NoArgsConstructor;
import work.WorkTool;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class WorkMap<W extends WorkTool> extends HashMap<String, Map<String, W>> {
    public WorkMap(String klass, String method, W work) {
        super();
        this.put(klass, method, work);
    }

    public void put(String klass, String method, W work) {
        if (work.isStatic() && CheckToken.isKlass(method) && klass.equals(KlassToken.DEFAULT_KLASS.get()))
            throw VariableException.STATIC_METHOD_NAME_ERROR.getThrow(method);
        if (super.containsKey(klass)) {
            Map<String, W> map = super.get(klass);
            if (map.containsKey(method))
                throw VariableException.DEFINE_METHOD.getThrow(klass + Token.ACCESS + method);
            else super.get(klass).put(method, work);
        }
        else super.put(klass, new HashMap<>(Map.of(method, work)));
    }

    public W get(String klass, String method) {
        Map<String, W> map = super.get(klass);
        if (map.containsKey(method)) return map.get(method);
        else throw VariableException.NO_DEFINE_METHOD.getThrow(method);
    }

    public boolean contains(String klass, String method) {
        return super.containsKey(klass) && super.get(klass).containsKey(method);
    }
}
