package cos.poison.controller;

import bin.token.Token;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import cos.poison.etc.PoisonToken;
import cos.poison.etc.PoisonException;
import cos.poison.etc.RexMap;
import cos.poison.item.PoisonItem;
import cos.poison.mode.MethodMode;
import lombok.AccessLevel;
import lombok.Setter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.regex.Matcher;

@Setter
public class HttpServerManager implements PoisonToken {
    private final Map<MethodMode, Map<String, BiFunction<HttpExchange, AtomicInteger, Object>>> method = new HashMap<>();
    private final Map<MethodMode, RexMap> rex = new HashMap<>();

    // 기본 모델
    // 경로를 추가하는 로직
    public void putMethod(MethodMode methodMode, String path,
                          BiFunction<HttpExchange, AtomicInteger, Object> consumer) {
        if (this.method.containsKey(methodMode)) {
            Map<String, BiFunction<HttpExchange, AtomicInteger, Object>> map = this.method.get(methodMode);
            if (map.containsKey(path)) throw PoisonException.HAVE_PATH_ERROR.getThrow(path);
        } else this.method.put(methodMode, new HashMap<>(Map.of(path, consumer)));
    }

    // 정규식 모델
    // 경로를 추가하는 로직
    public void putRex(MethodMode methodMode, Matcher matcher,
                       BiFunction<HttpExchange, AtomicInteger, Object> function) {
        if (this.rex.containsKey(methodMode)) {
            System.out.println();
            this.rex.get(methodMode).put(matcher, function);
        }
        else rex.put(methodMode, new RexMap(Map.of(matcher, function)));
    }

    public byte[] getMethod(MethodMode methodMode, String path, HttpExchange exchange, AtomicInteger statusCode) {
        if (this.method.containsKey(methodMode)) {
            Map<String, BiFunction<HttpExchange, AtomicInteger, Object>> map = this.method.get(methodMode);
            if (map.containsKey(path)) return (byte[]) map.get(path).apply(exchange, statusCode);
        }
        if (this.rex.containsKey(methodMode)) {
            return this.rex.get(methodMode).start(path, exchange, statusCode);
        }
        statusCode.set(HttpURLConnection.HTTP_NOT_FOUND);
        return null;
    }

    @Setter(AccessLevel.NONE)
    private HttpServer server;
    private String host = "localhost";
    private int port = 9090;

    public void create() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(this.host, this.port), 0);
        } catch (IOException e) {
            throw PoisonException.CREATE_SERVER_ERROR.getThrow(null);
        }
    }

    public void start(PoisonItem item) {
        if (this.server != null) {
            this.server.createContext("/", new HandlerRoot(item));
            this.server.start();
            System.out.printf("URL http://%s:%d/\n", this.host, this.port);
            this.printStart();
            PoisonToken.printMessage("[Poison Server Start]");
        } else throw PoisonException.NO_CREATE_SERVER.getThrow(null);
    }

    public void stop() {
        if (this.server != null) {
            this.server.stop(0);
            PoisonToken.printMessage("[Poison Server Stop]");
        } else throw PoisonException.NO_CREATE_SERVER.getThrow(null);
    }

    @Override
    public String toString() {
        return PoisonToken.POISON + Token.PARAM_S + Token.PARAM_E;
    }
}
