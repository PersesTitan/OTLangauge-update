package cos.poison.item;

import com.sun.net.httpserver.HttpExchange;

import java.util.concurrent.atomic.AtomicInteger;

public record HttpItem(HttpExchange exchange, AtomicInteger integer) {}
