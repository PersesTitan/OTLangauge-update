package cos.internet.item;

import bin.exception.SystemException;
import bin.variable.Types;
import bin.variable.custom.CustomList;
import cos.internet.etc.InternetException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class InternetItem {
    private final URL url;

    public InternetItem(String link) {
        try {
            this.url = new URL(link);
        } catch (MalformedURLException e) {
            throw InternetException.NOT_VALID_ERROR.getThrow(link);
        }
    }

    private URLConnection getConnection() {
        try {
            return this.url.openConnection();
        } catch (IOException e) {
            throw SystemException.CREATE_ERROR.getThrow("URLConnection");
        }
    }

    public CustomList<String> reader() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.url.openStream()))) {
            return new CustomList<>(Types.STRING, reader.lines().toList());
        } catch (IOException e) {
            throw SystemException.CREATE_ERROR.getThrow("URLConnection");
        }
    }
}
