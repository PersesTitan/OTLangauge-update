package cos.poison.handler;

import com.sun.net.httpserver.HttpExchange;
import cos.poison.etc.PoisonException;
import cos.poison.mode.DataType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HandlerPost {
    public static Map<String, Object> handler(HttpExchange exchange, DataType dataType) {
        Map<String, Object> parameter = new HashMap<>();
        try (InputStreamReader str = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(str)) {
            StringBuilder queryBuilder = new StringBuilder();
            reader.lines().forEach(queryBuilder::append);
            String query = queryBuilder.toString();
            HandlerTool.parsesQuery(dataType, query, parameter);
        } catch (IOException e) {
            throw PoisonException.DO_NOT_READ.getThrow(null);
        }
        return parameter;
    }
}
