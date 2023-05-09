package cos.poison.etc;

import bin.apply.item.VariableItem;
import bin.token.KlassToken;
import bin.token.SeparatorToken;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.controller.URLItem;

import java.net.HttpURLConnection;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RexMap extends HashMap<Matcher, BiFunction<HttpExchange, AtomicInteger, Object>> {
    public RexMap(Map<? extends Matcher, ? extends BiFunction<HttpExchange, AtomicInteger, Object>> m) {
        super(m);
    }

    public byte[] start(String path, HttpExchange exchange, AtomicInteger status) {
        try {
            for (Entry<Matcher, BiFunction<HttpExchange, AtomicInteger, Object>> entry : super.entrySet()) {
                if (entry.getKey().reset(path).find()) return (byte[]) entry.getValue().apply(exchange, status);
            }

            status.set(HttpURLConnection.HTTP_NOT_FOUND);
            return null;
        } catch (PatternSyntaxException e) {
            String errorMessage = SeparatorToken.SEPARATOR_LINE + "\t" +
                    e.getPattern().substring(3, e.getPattern().lastIndexOf('>')) +
                    SeparatorToken.SEPARATOR_LINE + "\t" +
                    " ".repeat(e.getIndex() - 3) + "^" +
                    SeparatorToken.SEPARATOR_LINE + "\t";
            throw PoisonException.URL_REGEXP_ERROR.getThrow(errorMessage);
        }
    }

    public static String castingHangle(String value) {
        StringBuilder builder = new StringBuilder("G");
        value.chars().forEach(v -> {
            if ((12593 <= v && v <= 12643) || (44032 <= v && v <= 55175)) builder.append(v);
            else if (v == '-') builder.append('-');
            else builder.append((char) v);
        });
        return builder.toString();
    }

//    public static Matcher makeMather(String key, VariableItem[] vars) {
//        for (VariableItem var : vars) {
//            String find = "<" + var.name() + ">";
//            if (key.contains(find)) {
//                URLItem item = URLItem.getType(var.type());
//                String hangle = RexMap.castingHangle(var.name());
//                key = key.replace(find, String.format("(?<%s>%s)", hangle, item.getText()));
//            } else throw PoisonException.URL_FIND_ERROR.getThrow(var.name());
//        }
//
//        try {
//            return Pattern.compile("^" + key + "$").matcher("");
//        } catch (PatternSyntaxException e) {
//            throw PoisonException.URL_SYNTAX_ERROR.getThrow(e.getDescription());
//        }
//    }

    public static Matcher makeMather(String path, VariableItem[] vars) {
        Set<VariableItem> set = new LinkedHashSet<>(Arrays.asList(vars));
        Matcher matcher = Pattern.compile(":[^/]+").matcher(path);
        while (matcher.find()) {
            // :변수명
            String group = matcher.group();
            VariableItem variableItem = getVariableItem(group.substring(1), set);
            set.remove(variableItem);
            URLItem item = URLItem.getType(variableItem.type());
            String hangle = RexMap.castingHangle(variableItem.name());
            path = path.replace(group, String.format("(?<%s>%s)", hangle, item.getText()));
        }
        if (!set.isEmpty()) throw PoisonException.URL_FIND_ERROR.getThrow(String.join(", ",
                    set.stream().map(VariableItem::name).toList()));
        try {
            return Pattern.compile('^' + path + '$').matcher("");
        } catch (PatternSyntaxException e) {
            throw PoisonException.URL_SYNTAX_ERROR.getThrow(e.getDescription());
        }
    }

    private static VariableItem getVariableItem(String variable, Set<VariableItem> set) {
        for (VariableItem item : set) {
            if (variable.equals(item.name())) return item;
        }
        throw PoisonException.URL_FIND_ERROR.getThrow(variable);
    }
}
