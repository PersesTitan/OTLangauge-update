package cos.poison.mode;

import bin.apply.ReplaceType;
import bin.exception.FileException;
import bin.token.KlassToken;
import bin.token.SeparatorToken;
import bin.token.Token;
import bin.variable.Types;
import cos.poison.etc.PoisonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum ResponseType {
    JS      ("application/x-javascript"),
    JSON    ("application/json"),
    PDF     ("application/pdf"),
    XML     ("application/xml"),
    CSS     ("text/css"),
    HTML    ("text/html"),
    IMAGE   ("image/jpeg"),
    SVG     ("image/svg+xml"),
    TEXT    ("text/plain")
    ;

    private final String mime;

    public static boolean isType(String type) {
        try {
            ResponseType.valueOf(type);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static ResponseType getType(String type) {
        try {
            return ResponseType.valueOf(type.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return TEXT;
        }
    }

    public static ResponseType getFileType(String fileName) {
        fileName = fileName.toLowerCase(Locale.ROOT);
        if (fileName.endsWith(".js")) return JS;
        else if (fileName.endsWith(".json")) return JSON;
        else if (fileName.endsWith(".pdf")) return PDF;
        else if (fileName.endsWith(".css")) return CSS;
        else if (fileName.endsWith(".xml")) return XML;
        else if (fileName.endsWith(".svg")
                || fileName.endsWith(".svgz")) return SVG;
        else if (fileName.endsWith(".html")
                || fileName.endsWith(".htm")) return HTML;
        else if (fileName.endsWith(".png")
                || fileName.endsWith(".jfif")
                || fileName.endsWith(".jfif-tbnl")
                || fileName.endsWith(".jpe")
                || fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg")) return IMAGE;
        else return TEXT;
    }

    public byte[] getBody(String value) {
        return switch (this) {
//            case TEXT -> Types.toString(Replace.replace(value)).getBytes(StandardCharsets.UTF_16);
            case TEXT -> Types.toString(ReplaceType.replace(KlassToken.STRING_VARIABLE, value))
                    .getBytes(StandardCharsets.UTF_16);
            default -> {
                File file = new File(value.replace(Token.ACCESS, SeparatorToken.SEPARATOR_FILE));
                if (!file.exists()) throw FileException.DO_NOT_PATH.getThrow(file.getPath());
                else if (!file.isFile()) throw FileException.FILE_TYPE_ERROR.getThrow(file.getPath());
                else if (!file.canRead()) throw FileException.DO_NOT_READ.getThrow(file.getPath());
                try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                    StringBuilder builder = new StringBuilder();
                    reader.lines().forEach(line -> builder.append(line).append(SeparatorToken.SEPARATOR_LINE));
                    yield builder.toString().getBytes(StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw FileException.DO_NOT_READ.getThrow(file.getPath());
                }
            }
        };
    }

    public void checkType(String fileName) {
        if (this.equals(TEXT)) return;
        if (!ResponseType.getFileType(fileName).equals(this)) {
            throw PoisonException.DATA_TYPE_ERROR.getThrow(fileName);
        }
    }
}
