package cos.poison.handler;

import bin.token.CheckToken;
import bin.token.EditToken;
import cos.poison.etc.PoisonException;
import cos.poison.mode.DataType;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

interface HandlerTool {
    static void parsesQuery(DataType dataType, String query, Map<String, Object> parameter) throws UnsupportedEncodingException {
        if (query == null) return;
        switch (dataType) {
            case JSON -> {
                if (CheckToken.bothCheck(query, '{', '}')) {
                    StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(query).strip(), ",");
                    while (tokenizer.hasMoreTokens()) {
                        String[] tokens = EditToken.split(tokenizer.nextToken(), ":");
                        String key = tokens[0].strip();
                        String value = tokens[1].strip();
                        parameter.put(
                                CheckToken.bothCheck(key, '"', '"') ? EditToken.bothCut(key) : key,
                                CheckToken.bothCheck(value, '"', '"') ? EditToken.bothCut(value) : value);
                    }
                } else throw PoisonException.DATA_PATTERN_ERROR.getThrow(dataType);
            }
            case URL -> {
                final String encoding = System.getProperty("file.encoding");
                StringTokenizer tokenizer = new StringTokenizer(query, "&");
                while (tokenizer.hasMoreTokens()) {
                    StringTokenizer paramTokenizer = new StringTokenizer(tokenizer.nextToken(), "=");
                    String key = paramTokenizer.hasMoreTokens()
                            ? URLDecoder.decode(paramTokenizer.nextToken(), encoding)
                            : null;
                    String value = paramTokenizer.hasMoreTokens()
                            ? URLDecoder.decode(paramTokenizer.nextToken(), encoding)
                            : "";
                    if (parameter.containsKey(key)) {
                        Object obj = parameter.get(key);
                        if (obj instanceof List<?>) return;
                        else if (obj instanceof String str) {
                            parameter.put(key, new ArrayList<>(List.of(str, value)));
                        }
                    } else parameter.put(key, value);
                }
            }
        }
    }
}
