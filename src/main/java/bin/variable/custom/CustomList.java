package bin.variable.custom;

import bin.exception.VariableException;
import bin.token.CheckToken;
import bin.token.Token;
import bin.variable.Types;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static bin.token.Token.FALSE;
import static bin.token.Token.TRUE;

@Getter
@RequiredArgsConstructor
public class CustomList<V> extends LinkedList<V> implements CustomTool {
    private final Types types;

    public CustomList(Types types, Collection<V> collection) {
        super(collection);
        this.types = types;
    }

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
            if (CheckToken.isList(value)) super.addAll((CustomList<V>) types.getListWork().create(value));
            else super.add((V) types.originCast(value));
        } catch (Exception e) {
            throw VariableException.VALUE_ERROR.getThrow(value);
        }
    }

    public void addStr(String value) {
        this.add(value);
    }

    public V get(String value) {
        int i = (int) Types.INTEGER.originCast(value);
        try {
            return super.get(i);
        } catch (Exception e) {
            throw VariableException.ACCESS_ERROR.getThrow(Integer.toString(i));
        }
    }

    public Object sum() {
        return this.sum(this.types, super.stream(), this.types.getListType());
    }

    public Object max() {
        return this.max(this.types, super.stream(), this.types.getListType());
    }

    public Object min() {
        return this.min(this.types, super.stream(), this.types.getListType());
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
