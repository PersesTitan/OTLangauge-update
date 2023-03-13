package bin.repository;

import bin.Repository;
import bin.exception.VariableException;
import bin.token.CheckToken;
import bin.token.KlassToken;
import bin.token.Token;
import bin.variable.Types;
import lombok.Getter;
import work.CreateWork;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HpMap extends HashMap<String, Object> {
    private final Map<String, Integer> hp = new HashMap<>();
    private final CreateWork<?> createWork;
    private final String klassType;

    public HpMap(String klassType) {
        if (CheckToken.isKlass(klassType)) {
            this.klassType = klassType;
            this.createWork = Repository.createWorks.get(klassType);
        } else throw VariableException.NO_DEFINE_TYPE.getThrow(klassType);
    }

    @Override
    public boolean containsKey(Object key) {
        String k = key.toString();
        if (k.charAt(0) == '[') k = k.substring(k.indexOf(']') + 1);
        return super.containsKey(k);
    }

    @Override
    public Object get(Object key) {
        String k = key.toString();
        if (this.hp.containsKey(k)) {
            int i = this.hp.get(k);
            if (--i == 0) this.remove(key);
            else this.hp.replace(k, i);
        }
        return super.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        if (key.charAt(0) == '[') {
            int right = key.indexOf(']');
            if (right == -1) throw VariableException.VARIABLE_NAME_ERROR.getThrow(key);
            int hpValue = (int) Types.INTEGER.originCast(key.substring(1, right));
            // 값이 0이하일때 값을 넣지 않음
            if (hpValue <= 0) return null;
            hp.put(key, hpValue);
            key = key.substring(right + 1);
        }
        // 변수명이 유효하지 않을때
        if (!key.matches(Token.VARIABLE))
            throw VariableException.VARIABLE_NAME_ERROR.getThrow(key);
        // 변수명이 클래스명과 같을때
        if (Repository.createWorks.containsKey(key))
            throw VariableException.VARIABLE_NAME_CLASS.getThrow(key);
        // 사용할 수 없는 문자를 사용하였을때
        if (this.noUse(key)) throw VariableException.VARIABLE_NAME_NOT_USE.getThrow(key);

        return this.createWork.check(value)
                ? super.put(key, value)
                : super.put(key, this.createWork.create(Types.toString(value)));
    }

    @Override
    public Object replace(String key, Object value) {
        if (this.createWork.check(value)) {
            return super.replace(key, value);
        } else return super.replace(key, this.createWork.create(Types.toString(value)));
    }

    private boolean noUse(String key) {
        for (String token : Token.NO_USE) {
            if (key.contains(token)) return true;
        }
        return false;
    }

    @Override
    public Object remove(Object key) {
        this.hp.remove(key);
        return super.remove(key);
    }
}