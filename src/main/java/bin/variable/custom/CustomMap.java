package bin.variable.custom;

import bin.exception.Error;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.Token;
import bin.variable.Types;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static bin.token.Token.FALSE;
import static bin.token.Token.TRUE;

@Getter
@RequiredArgsConstructor
public class CustomMap<K, V> extends LinkedHashMap<K, V> {
    private final Types keyKlass;
    private final Types valueKlass;

    @Override
    public boolean containsKey(Object key) {
        if (key instanceof String str) {
            return Types.STRING.equals(this.keyKlass)
                    ? super.containsKey(str)
                    : super.containsKey(this.keyKlass.originCast(str));
        } else return super.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if (value instanceof String str) {
            return Types.STRING.equals(this.valueKlass)
                    ? super.containsKey(str)
                    : super.containsKey(this.valueKlass.originCast(str));
        } else return super.containsKey(value);
    }

    public void put(String value) {
        try {
            if (value.startsWith(Token.MAP_S) && value.endsWith(Token.MAP_E))
                super.putAll((CustomMap<K, V>) valueKlass.getMapWork().create(value));
            else {
                if (value.contains(Token.MAP_CENTER)) {
                    String[] tokens = value.split(Token.MAP_CENTER, 2);
                    K k = (K) keyKlass.originCast(tokens[0].strip());
                    V v = (V) valueKlass.originCast(tokens[1].strip());
                    super.put(k, v);
                } else throw MatchException.MAP_MATCH_ERROR.getThrow(value);
            }
        } catch (Error e) {
            throw e;
        } catch (Exception e) {
            throw VariableException.VALUE_ERROR.getThrow(value);
        }
    }

    public Object sumValue() {
        return switch (this.valueKlass) {
            case CHARACTER, INTEGER -> super.values().stream().mapToInt(v -> (int) v).sum();
            case LONG -> super.values().stream().mapToLong(v -> (long) v).sum();
            case FLOAT -> (float) super.values().stream().mapToDouble(v -> (float) v).sum();
            case DOUBLE -> super.values().stream().mapToDouble(v -> (double) v).sum();
            case STRING, BOOLEAN -> throw VariableException.TYPE_ERROR.getThrow(this.valueKlass.getMapType());
        };
    }

    @Override
    public V get(Object key) {
        if (super.containsKey(key)) return super.get(key);
        else throw VariableException.ACCESS_ERROR.getThrow(Types.toString(key));
    }

    @Override
    public String toString() {
        Iterator<Map.Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext()) return Token.MAP_S.concat(Token.MAP_E);

        StringBuilder sb = new StringBuilder(Token.MAP_S);
        for (;;) {
            Map.Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            if (key instanceof Boolean bool) sb.append(bool ? TRUE : FALSE);
            else sb.append(key);
            sb.append('=');
            if (value instanceof Boolean bool) sb.append(bool ? TRUE : FALSE);
            else sb.append(value);
            if (! i.hasNext()) return sb.append(Token.MAP_E).toString();
            sb.append(',').append(' ');
        }
    }
}
