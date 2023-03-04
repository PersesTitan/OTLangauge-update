package bin.apply.calculator.bool;

import bin.exception.MatchException;
import bin.token.Token;
import bin.variable.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BoolCalculator extends BoolTool {
    private static BoolCalculator INSTANCE;
    public static BoolCalculator getInstance() {
        if (INSTANCE == null) {
            synchronized (BoolCalculator.class) {
                INSTANCE = new BoolCalculator();
            }
        }
        return INSTANCE;
    }

    // ㅇㄴㄸㄲ
    private final String OR_TOKENS = Token.NOT.concat(Token.OR).concat(Token.AND);
    public boolean calculator(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, OR_TOKENS, true);
        Stack<String> stack = new Stack<>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            switch (token) {
                case "ㅇ" -> {
                    if (tokenizer.hasMoreTokens()) {
                        String a = tokenizer.nextToken();
                        switch (a) {
                            case "ㅇ" -> stack.add(Token.TRUE);
                            case "ㄴ" -> stack.add(Token.NOT);
                            case Token.SMALL, Token.BIG, Token.SAME, Token.SMALL_SAME, Token.BIG_SAME -> {
                                if (tokenizer.hasMoreTokens()) {
                                    String b = tokenizer.nextToken();
                                    if (b.equals("ㅇ")) stack.add(a);
                                    else this.addBack(stack, token.concat(a).concat(b));
                                } else this.addBack(stack, token.concat(a));
                            }
                            default -> this.addBack(stack, token.concat(a));
                        }
                    } else this.addBack(stack, token);
                }
                case "ㄴ" -> {
                    if (tokenizer.hasMoreTokens()) {
                        String a = tokenizer.nextToken();
                        if (a.equals("ㄴ")) stack.add(Token.FALSE);
                        else this.addBack(stack, token.concat(a));
                    } else this.addBack(stack, token);
                }
                case Token.OR -> stack.add(Token.OR);
                case Token.AND -> stack.add(Token.AND);
                default -> this.addBack(stack, token);
            }
        }

        List<Object> list = stack.stream()
                .map(String::strip)
                .filter(Predicate.not(String::isEmpty))
                .map(token -> switch (token) {
                    case Token.OR, Token.AND, Token.NOT,
                            Token.SMALL, Token.BIG, Token.SAME,
                            Token.SMALL_SAME, Token.BIG_SAME -> token;
                    case Token.TRUE -> true;
                    case Token.FALSE -> false;
                    default -> Types.toObject(token);
                })
                .collect(Collectors.toList());

        this.compare.forEach((k, v) -> {
            int i;
            while ((i = list.indexOf(k)) >= 0) {
                Object last = list.remove(i + 1);
                list.remove(i);
                list.set(i-1, v.apply(list.get(i-1), last));
            }
        });
        int i;
        while ((i = list.indexOf(Token.NOT)) >= 0) {
            list.remove(i);
            list.set(i, !((boolean) list.get(i)));
        }
        while (list.size() > 1) {
            boolean last = (boolean) list.remove(2);
            String sing = Types.toString(list.remove(1));
            if (logical.containsKey(sing)) {
                boolean first = (boolean) list.get(0);
                list.set(0, logical.get(sing).apply(first, last));
            } else throw MatchException.SING_NUMBER_ERROR.getThrow(sing);
        }

        return (boolean) list.get(0);
    }
}
