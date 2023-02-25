package bin.repository;

import bin.exception.VariableException;

import java.util.HashMap;
import java.util.Map;

public class WorkMap<W> extends HashMap<String, Map<String, W>> {
    public void put(String klass, String method, W work) {
        if (super.containsKey(klass)) super.get(klass).put(method, work);
        else super.put(klass, new HashMap<>(Map.of(method, work)));
    }

    public W get(String klass, String method) {
        Map<String, W> map = super.get(klass);
        if (map.containsKey(method)) return super.get(klass).get(method);
        else throw VariableException.NO_DEFINE_NAME.getThrow(method);
    }

    public boolean contains(String klass, String method) {
        return super.containsKey(klass) && super.get(klass).containsKey(method);
    }
}
