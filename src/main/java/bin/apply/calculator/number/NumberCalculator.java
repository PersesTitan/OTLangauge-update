package bin.apply.calculator.number;

import bin.apply.Replace;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.CheckToken;
import bin.token.Token;
import bin.variable.Types;

import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.function.BiFunction;

public class NumberCalculator extends NumberTool {
    private static NumberCalculator INSTANCE;
    public static NumberCalculator getInstance() {
        if (INSTANCE == null) {
            synchronized (LongCalculator.class) {
                INSTANCE = new NumberCalculator();
            }
        }
        return INSTANCE;
    }

    public final Map<String, BiFunction<Object, Object, Object>> numbers = Map.of(
            Token.SUM_TOKEN,        this::sum,
            Token.MINUS_TOKEN,      this::minus,
            Token.MULTIPLY_TOKEN,   this::multiply,
            Token.DIVIDE_TOKEN,     this::divide,
            Token.REMAIN_TOKEN,     this::remain
    );

    public Object calculator(String line) {
        Stack<Object> stack = this.subCalculator(line);
        int i;
        while ((i = indexOf(stack)) >= 0) this.setStack(stack, i);
        while (stack.size() > 1) this.setStack(stack, 2);
        return stack.firstElement();
    }

    private void setStack(Stack<Object> stack, int i) {
        Object last = stack.remove(i + 1);
        String sing = Types.toString(stack.remove(i));
        if (numbers.containsKey(sing)) {
            Object value = numbers.get(sing).apply(stack.get(i - 1), last);
            stack.set(i - 1, value);
        } else throw MatchException.SING_NUMBER_ERROR.getThrow(sing);
    }

    // 안녕 ㅇ+ㅇ 안녕2 => 안녕
    public Types getCalculatorTypes(String value) {
        StringBuilder builder = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(value, Token.CALCULATOR_TOKEN, true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.equals(Token.CALCULATOR_TOKEN) && tokenizer.hasMoreTokens()) {
                String a = tokenizer.nextToken();
                if (numbers.containsKey(a) && tokenizer.hasMoreTokens()) {
                    String b = tokenizer.nextToken();
                    if (b.equals(Token.CALCULATOR_TOKEN)) break;
                    else builder.append(token).append(a).append(b);
                } else builder.append(token).append(a);
            } else builder.append(token);
        }

        String token = builder.toString().strip();
        if (CheckToken.isInteger(token)) return Types.INTEGER;
        else if (CheckToken.isLong(token)) return Types.LONG;
        else if (token.length() == 1) return Types.CHARACTER;
        else if (CheckToken.isFloat(token)) return Types.FLOAT;
        else if (CheckToken.isDouble(token)) return Types.DOUBLE;
        else {
            Types types = Types.getTypes(Replace.replace(token));
            return switch (types) {
                case INTEGER, LONG, FLOAT, DOUBLE -> types;
                default -> throw VariableException.TYPE_ERROR.getThrow(token);
            };
        }
    }

    public static boolean haveNumber(String value) {
        return NumberCalculator.getInstance().numbers.keySet()
                .stream()
                .anyMatch(value::contains);
    }
}
