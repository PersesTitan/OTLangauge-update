package bin.variable.custom;

import bin.exception.VariableException;
import bin.token.Token;
import bin.variable.Types;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.LinkedList;

import static bin.token.Token.FALSE;
import static bin.token.Token.TRUE;

@Getter
@RequiredArgsConstructor
public class CustomList<V> extends LinkedList<V> {
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
            super.addAll((CustomList<V>) types.getListWork().create(value));
        } catch (Exception e) {
            throw VariableException.VALUE_ERROR.getThrow(value);
        }
    }

    public V get(String value) {
        int i = (int) Types.INTEGER.originCast(value);
        try {
            return super.get(i);
        } catch (Exception e) {
            throw VariableException.ACCESS_ERROR.getThrow(Integer.toString(i));
        }
    }

    @Override
    public String toString() {
        Iterator<V> it = iterator();
        if (! it.hasNext()) return Token.LIST_S.concat(Token.LIST_E);

        StringBuilder sb = new StringBuilder(Token.LIST_S);
        for (;;) {
            V e = it.next();
            if (e instanceof Boolean bool) sb.append(bool ? TRUE : FALSE);
            else sb.append(e);
            if (! it.hasNext()) return sb.append(Token.LIST_E).toString();
            sb.append(',').append(' ');
        }
    }
}
