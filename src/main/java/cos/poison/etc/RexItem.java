package cos.poison.etc;

import bin.apply.item.VariableItem;
import com.sun.net.httpserver.HttpExchange;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public record RexItem(VariableItem[] vars, String[] hangle, BiFunction<HttpExchange, AtomicInteger, Object> function) {
    public RexItem(VariableItem[] vars, BiFunction<HttpExchange, AtomicInteger, Object> function) {
        this(vars, Arrays.stream(vars)
                        .map(VariableItem::name)
                        .map(RexMap::castingHangle)
                        .toArray(String[]::new), function);
    }

    public byte[] start(HttpExchange exchange, AtomicInteger status,
                        BiFunction<HttpExchange, AtomicInteger, Object> function) {
        return (byte[]) function.apply(exchange, status);
    }
}
