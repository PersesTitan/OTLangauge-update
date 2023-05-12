package cos.poison.handler;

import com.sun.net.httpserver.HttpExchange;
import cos.poison.etc.PoisonException;
import cos.poison.mode.DataType;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HandlerGet {
    public static Map<String, Object> handler(HttpExchange exchange, DataType dataType) {
        Map<String, Object> parameters = new HashMap<>();
        String query = exchange.getRequestURI().getRawQuery();
        try {
            HandlerTool.parsesQuery(dataType, query, parameters);
        } catch (UnsupportedEncodingException e) {
            throw PoisonException.DO_NOT_READ.getThrow(null);
        }
        return parameters;
    }
}
