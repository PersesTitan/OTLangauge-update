package bin.apply.calculator.number;

import bin.exception.MatchException;
import bin.token.Token;
import bin.variable.Types;

import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;

public class DoubleCalculator extends NumberTool {
    private static DoubleCalculator INSTANCE;
    public static DoubleCalculator getInstance() {
        if (INSTANCE == null) {
            synchronized (DoubleCalculator.class) {
                INSTANCE = new DoubleCalculator();
            }
        }
        return INSTANCE;
    }

    private final Map<String, BiFunction<Double, Double, Double>> numbers = Map.of(
            Token.SUM_TOKEN,        Double::sum,
            Token.MINUS_TOKEN,      (a, b) -> a - b,
            Token.MULTIPLY_TOKEN,   (a, b) -> a * b,
            Token.DIVIDE_TOKEN,     (a, b) -> a / b,
            Token.REMAIN_TOKEN,     (a, b) -> a % b
    );

    public double calculator(String line) {
        Stack<Object> stack = this.subCalculator(line);
        int i;
        while ((i = indexOf(stack)) >= 0) this.setStack(stack, i);
        while (stack.size() > 1) this.setStack(stack, 1);
        return (double) stack.firstElement();
    }

    private void setStack(Stack<Object> stack, int i) {
        double last = (double) stack.remove(i + 1);
        String sing = Types.toString(stack.remove(i));
        if (this.numbers.containsKey(sing)) {
            double value = this.numbers.get(sing).apply((double) stack.get(i - 1), last);
            stack.set(i - 1, value);
        } else throw MatchException.SING_NUMBER_ERROR.getThrow(sing);
    }
}
