package bin.variable.custom;

import bin.exception.VariableException;
import bin.token.Token;
import bin.variable.Types;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.LinkedHashSet;

import static bin.token.Token.FALSE;
import static bin.token.Token.TRUE;

@Getter
@RequiredArgsConstructor
public class CustomSet<T> extends LinkedHashSet<T> {
    private final Types types;

    @Override
    public boolean contains(Object o) {
        if (o instanceof String str) {
            return Types.STRING.equals(types)
                    ? super.contains(str)
                    : super.contains(types.originCast(str));
        } else return super.contains(o);
    }

    public void add(String value) {
        try {
            super.addAll((CustomSet<T>) types.getListWork().create(value));
        } catch (Exception e) {
            throw VariableException.VALUE_ERROR.getThrow(value);
        }
    }

    public T get(String value) {
        return get((int) Types.INTEGER.originCast(value));
    }

    public T get(int i) {
        int count = 0;
        Iterator<T> iterator = super.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (count++ == i) return t;
        }
        throw VariableException.ACCESS_ERROR.getThrow(Integer.toString(i));
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        if (! it.hasNext()) return Token.SET_S.concat(Token.SET_E);

        StringBuilder sb = new StringBuilder();
        sb.append(Token.SET_S);
        for (;;) {
            T e = it.next();
            // 블린형일때
            if (e instanceof Boolean bool) sb.append(bool ? TRUE : FALSE);
            else sb.append(e);
            if (! it.hasNext()) return sb.append(Token.SET_E).toString();
            sb.append(',').append(' ');
        }
    }
}
