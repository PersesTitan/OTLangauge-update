package cos.poison.mode;

import com.sun.net.httpserver.HttpExchange;
import cos.poison.etc.PoisonToken;
import cos.poison.etc.PoisonException;
import cos.poison.handler.HandlerGet;
import cos.poison.handler.HandlerPost;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

public enum MethodMode {
    // 민트색
    POST    ("\033[30m\033[102m", HandlerPost::handler),
    // 초록색
    GET     ("\033[30m\033[106m", HandlerGet::handler),
    // 노란색
    PUT     ("\033[30m\033[103m", HandlerGet::handler),
    // 파란색
    PATCH   ("\033[30m\033[104m", HandlerGet::handler),
    // 빨간색
    DELETE  ("\033[30m\033[101m", HandlerGet::handler),
//    HEAD    ("\033[30m\033[107m"),  // 하얀색
//    OPTIONS ("\033[30m\033[100m"),  // 검은색
//    CONNECT ("\033[30m\033[105m")   // 보라색
    ;


    private final String print;
    private final BiFunction<HttpExchange, DataType, Map<String, Object>> function;

    MethodMode(String color, BiFunction<HttpExchange, DataType, Map<String, Object>> function) {
        this.print = String.format("%s %s %s\033[0m", color, name(), " ".repeat(7-name().length()));
        this.function = function;
    }

    public void print(String path, String query) {
        PoisonToken.printTime();
        System.out.print(this.print);
        System.out.print(" [경로] ");
        System.out.printf("\033[1;34m %s \033[0m", path == null ? "" : path);
        System.out.print(" | [값] ");
        System.out.printf("\033[1;34m %s \033[0m", query == null ? "" : query);
        System.out.println();
    }

    public Map<String, Object> getParams(HttpExchange exchange, DataType dataType) {
        return this.function.apply(exchange, dataType);
    }

    public static MethodMode get(String method) {
        try {
            return MethodMode.valueOf(method.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw PoisonException.METHOD_TYPE_ERROR.getThrow(method);
        }
    }
}
