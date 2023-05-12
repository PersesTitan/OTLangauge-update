package cos.poison.controller;

import bin.Repository;
import bin.apply.item.VariableItem;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.SeparatorToken;
import cos.poison.etc.PoisonException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class URLParser {
    private final List<Matcher> list = new ArrayList<>();

    // 패턴 등록하는 로직
    public void apply(String url, VariableItem... vars) {
        for (VariableItem var : vars) {
            String find = "<" + var.name() + ">";
            if (url.contains(find)) {
                URLItem item = URLItem.getType(var.type());
                url = url.replace(find, this.makeGroup(this.castingHangle(var.name()), item.getText()));
            } else throw PoisonException.URL_FIND_ERROR.getThrow(var.type());
        }

        try {
            this.list.add(Pattern.compile("^" + url + "$").matcher(""));
        } catch (PatternSyntaxException e) {
            throw PoisonException.URL_SYNTAX_ERROR.getThrow(e.getDescription());
        }
    }

    // 그룹이 된 값을 가져오는 로직
    public Object[] start(String url, VariableItem... names) {
        try {
            int len = names.length;
            for (Matcher matcher : this.list) {
                if (matcher.reset(url).find()) {
                    Object[] os = new Object[len];
                    for (int i = 0; i<len; i++) {
                        String name = this.castingHangle(names[i].name());
                        String value = matcher.group(name);
                        os[i] = Repository.createWorks.get(names[i].type()).create(value);
                    }
                    return os;
                }
            }
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

    private String castingHangle(String value) {
        StringBuilder builder = new StringBuilder("G");
        value.chars().forEach(v -> {
            if ((12593 <= v && v <= 12643) || (44032 <= v && v <= 55175)) builder.append(v);
            else if (v == '-') builder.append('-');
            else builder.append((char) v);
        });
        return builder.toString();
    }

    // make group type
    private String makeGroup(String name, String regex) {
        return String.format("(?<%s>%s)", name, regex);
    }

    public static void main(String[] args) {
        URLParser parser = new URLParser();
        VariableItem item1 = new VariableItem(KlassToken.INT_VARIABLE, "값1");
        VariableItem item2 = new VariableItem(KlassToken.STRING_VARIABLE, "값2");
        parser.apply("/<값1>/<값2>/", item1, item2);
        System.out.println(Arrays.toString(parser.start("/13/dkssu/", item1)));
    }
}
