package bin.repository;

import bin.Repository;
import bin.exception.VariableException;
import bin.token.CheckToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

public class TypeMap extends HashMap<String, HpMap> {
    public void create(String type, String name, Object value) {
        // 존재하는 클래스 타입인지 확인
        if (!CheckToken.isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
        if (!super.isEmpty()) {
            if (defineVariableName(name)) throw VariableException.DEFINE_NAME.getThrow(name);
        }
        if (super.containsKey(type)) super.get(type).put(name, value);
        else super.put(type, new HpMap(type) {{put(name, value);}});
    }

    // 변수명이 존재하는지 확인
    private boolean defineVariableName(String name) {
        return super.values()
                .stream()
                .anyMatch(v -> v.containsKey(name));
    }

    public void update(String type, String name, Object value) {
        super.get(type).replace(name, value);
    }

    public void update(String name, Object value) {
        for (HpMap map : super.values()) {
            if (map.containsKey(name)) {
                map.replace(name, value);
                return;
            }
        }
        throw VariableException.NO_DEFINE_NAME.getThrow(name);
    }

    public Object find(String name) {
        for (HpMap map : super.values()) {
            if (map.containsKey(name)) return map.get(name);
        }
        throw VariableException.NO_DEFINE_NAME.getThrow(name);
    }

    public Object find(String type, String name) {
        return super.get(type).get(name);
    }

    public HpMap findMap(String name) {
        return super.values().stream()
                .filter(v -> v.containsKey(name))
                .findFirst()
                .orElseThrow(() -> VariableException.NO_DEFINE_NAME.getThrow(name));
    }

    public boolean findVar(String name) {
        if (super.isEmpty()) return false;
        return super.values()
                .stream()
                .anyMatch(v -> v.containsKey(name));
    }

    public boolean findVar(String type, String name) {
        return super.containsKey(type) && super.get(type).containsKey(name);
    }

    public String getKlass(String name) {
        for (HpMap map : super.values()) {
            if (map.containsKey(name)) return map.getKlassType();
        }
        throw VariableException.NO_DEFINE_NAME.getThrow(name);
    }
}
