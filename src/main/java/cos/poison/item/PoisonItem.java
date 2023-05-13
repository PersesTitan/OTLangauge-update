package cos.poison.item;

import bin.token.EditToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.controller.HttpServerManager;
import cos.poison.etc.PoisonException;
import cos.poison.mode.DataType;
import cos.poison.mode.ResponseType;
import lombok.Getter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public class PoisonItem {
    private final HttpServerManager manager = new HttpServerManager();
    private final AtomicReference<HttpItem> httpItem = new AtomicReference<>(null);
    private final AtomicReference<ResponseType> responseType = new AtomicReference<>(ResponseType.TEXT);
    private final AtomicReference<DataType> dataType = new AtomicReference<>(DataType.URL);

    public DataType getDataType() {
        return this.dataType.get();
    }

    public void setDataType(String value) {
        this.dataType.set(DataType.casting(value));
    }

    public HttpItem getHttpItem() {
        HttpItem item = this.httpItem.get();
        if (item == null) throw PoisonException.DO_NOT_RUN.getThrow(null);
        return httpItem.get();
    }

    public void setHttpItem(HttpExchange exchange, AtomicInteger statusCode) {
        this.httpItem.set(new HttpItem(exchange, statusCode));
    }

    public void setHttpItem(HttpItem httpItem) {
        this.httpItem.set(httpItem);
    }

    public void setResponseType(String type) {
        this.responseType.set(ResponseType.getType(type));
    }

    public ResponseType getResponseType() {
        return this.responseType.get();
    }

    public void setHost(String host) {
        this.manager.setHost(host);
    }

    public void setPort(int port) {
        this.manager.setPort(port);
    }

    public void start() {
        this.manager.start(this);
    }

    public void stop() {
        this.manager.stop();
    }

    public void create() {
        this.manager.create();
    }

    public void setType(String type) {
        setResponseType(type);
    }

    public String getCookie(String key) {
        Headers headers = getHttpItem().exchange().getRequestHeaders();

        List<String> cookie = headers.getOrDefault("Cookie", null);
        if (cookie == null || cookie.isEmpty()) return "";
        for (String cookieItem : cookie) {
            StringTokenizer cookies = new StringTokenizer(cookieItem, ";");
            while (cookies.hasMoreTokens()) {
                String[] tokens = EditToken.split(cookies.nextToken(), "=");
                if (tokens[0].strip().equals(key)) return tokens[1];
            }
        }
        return "";
    }

    public boolean isCookie(String key) {
        Headers headers = getHttpItem().exchange().getRequestHeaders();
        if (headers.containsKey("Cookie")) {
            List<String> cookie = headers.get("Cookie");
            if (cookie.isEmpty()) return false;
            for (String cookieItem : cookie) {
                StringTokenizer cookies = new StringTokenizer(cookieItem, ";");
                while (cookies.hasMoreTokens()) {
                    String[] tokens = EditToken.split(cookies.nextToken(), "=");
                    if (tokens[0].strip().equals(key)) return true;
                }
            }
        }
        return false;
    }

    public void deleteCookie(String key, String path) {
        Headers headers = getHttpItem().exchange().getResponseHeaders();
        headers.add("Set-Cookie", key + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=" + path);
    }

    public void redirect(String path) {
        getHttpItem().exchange()
                .getResponseHeaders()
                .add("Location", path);
    }
}
