package bin.apply.calculator.bool;

import bin.exception.VariableException;
import bin.token.Token;

import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;

class BoolTool {
    final Map<String, BiFunction<Object, Object, Boolean>> compare = Map.of(
            Token.SMALL,        (a, b) -> getNumber(a) < getNumber(b),
            Token.BIG,          (a, b) -> getNumber(a) > getNumber(b),
            Token.SAME,         (a, b) -> getNumber(a) == getNumber(b),
            Token.SMALL_SAME,   (a, b) -> getNumber(a) <= getNumber(b),
            Token.BIG_SAME,     (a, b) -> getNumber(a) >= getNumber(b)
    );

    final Map<String, BiFunction<Boolean, Boolean, Boolean>> logical = Map.of(
            Token.OR,   (a, b) -> a || b,
            Token.AND,  (a, b) -> a && b
    );

    // (ㄲ, ㄸ)
    int index(Stack<Object> stack) {
        ListIterator<Object> it = stack.listIterator();
        while (it.hasNext()) {
            Object token = it.next();
            if (token.equals(Token.OR) || token.equals(Token.AND)) {
                return it.previousIndex();
            }
        }
        return -1;
    }

    void addBack(Stack<String> stack, String token) {
        if (stack.isEmpty() || this.isToken(stack.lastElement())) stack.add(token);
        else stack.add(stack.pop().concat(token));
    }

    double getNumber(Object object) {
        if (object instanceof Integer i) return i;
        else if (object instanceof Long l) return l;
        else if (object instanceof Float f) return f;
        else if (object instanceof Double d) return d;
        else throw VariableException.TYPE_ERROR.getThrow(object);
    }

    boolean isToken(String token) {
        return switch (token) {
            case Token.TRUE, Token.FALSE, Token.NOT, Token.OR, Token.AND -> true;
            default -> this.compare.containsKey(token);
        };
    }
}
