package cos.poison.controller;

import bin.apply.mode.DebugMode;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import cos.poison.etc.PoisonException;
import cos.poison.item.PoisonItem;
import cos.poison.mode.MethodMode;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class HandlerRoot implements HttpHandler {
    private final PoisonItem poisonItem;

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        try (exchange; OutputStream responseBody = exchange.getResponseBody()) {
            AtomicInteger statusCode = new AtomicInteger(HttpURLConnection.HTTP_OK);
            AtomicReference<String> nowPath = new AtomicReference<>(path);

            MethodMode methodMode = MethodMode.get(exchange.getRequestMethod());

//            Headers responseHeader = exchange.getResponseHeaders(); // 응답(send)
//            Headers requestHeader  = exchange.getRequestHeaders();  // 요청(get)

            this.poisonItem.setHttpItem(exchange, statusCode);
            try {
                byte[] body = this.poisonItem.getManager().getMethod(methodMode, path, exchange, statusCode);
                exchange.sendResponseHeaders(statusCode.get(),0);
                if (body != null) responseBody.write(body);
            } catch (Exception e) {
                if (DebugMode.isDevelopment()) e.printStackTrace();
            }
        } catch (IOException e) {
            throw PoisonException.DO_NOT_READ.getThrow(null);
        } finally {
            this.poisonItem.setHttpItem(null);
        }
    }
}
