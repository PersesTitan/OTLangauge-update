package bin.apply.calculator.number;

import bin.exception.MatchException;
import bin.token.KlassToken;
import bin.token.Token;
import bin.variable.Types;

import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;

public class LongCalculator extends NumberTool {
    private static LongCalculator INSTANCE;
    public static LongCalculator getInstance() {
        if (INSTANCE == null) {
            synchronized (LongCalculator.class) {
                INSTANCE = new LongCalculator();
            }
        }
        return INSTANCE;
    }

    private final Map<String, BiFunction<Long, Long, Long>> numbers = Map.of(
            Token.SUM_TOKEN,        Long::sum,
            Token.MINUS_TOKEN,      (a, b) -> a - b,
            Token.MULTIPLY_TOKEN,   (a, b) -> a * b,
            Token.DIVIDE_TOKEN,     (a, b) -> a / b,
            Token.REMAIN_TOKEN,     (a, b) -> a % b
    );

    public long calculator(String line) {
        Stack<Object> stack = this.subCalculator(line, KlassToken.LONG_VARIABLE);
        int i;
        while ((i = indexOf(stack)) >= 0) this.setStack(stack, i);
        while (stack.size() > 1) this.setStack(stack, 2);
        return (long) stack.firstElement();
    }

    private void setStack(Stack<Object> stack, int i) {
        long last = (long) stack.remove(i + 1);
        String sing = Types.toString(stack.remove(i));
        if (this.numbers.containsKey(sing)) {
            long value = this.numbers.get(sing).apply((long) stack.get(i - 1), last);
            stack.set(i - 1, value);
        } else throw MatchException.SING_NUMBER_ERROR.getThrow(sing);
    }
}
