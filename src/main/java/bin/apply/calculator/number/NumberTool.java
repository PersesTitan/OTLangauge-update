package bin.apply.calculator.number;

import bin.apply.ReplaceType;
import bin.exception.VariableException;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;

import java.util.ListIterator;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class NumberTool {
    /// 변수명 ㅇ+ㅇ 값 => [변수명, +, 값]
    Stack<Object> subCalculator(String line, String type) {
        StringTokenizer tokenizer = new StringTokenizer(line, Token.CALCULATOR_TOKEN, true);
        Stack<String> stack = new Stack<>();
        while (tokenizer.hasMoreTokens()) {
            String a = tokenizer.nextToken();
            if (a.equals(Token.CALCULATOR_TOKEN) && tokenizer.hasMoreTokens()) {
                // if a = (TOKEN) ㅇ
                String b = tokenizer.nextToken();
                if (NumberCalculator.getInstance().numbers.containsKey(b) && tokenizer.hasMoreTokens()) {
                    // if b = (+, -, *, /, %)
                    String c = tokenizer.nextToken();
                    if (c.equals(Token.CALCULATOR_TOKEN)) stack.add(b);
                    else this.isSing(stack, a.concat(b).concat(c), tokenizer);
                } else this.isSing(stack, a.concat(b), tokenizer);
            } else this.isSing(stack, a, tokenizer);
        }

        return stack.stream()
                .map(String::strip)
//                .map(v -> NumberCalculator.getInstance().numbers.containsKey(v) ? v : Replace.replace(v))
                .map(v -> {
                    if (NumberCalculator.getInstance().numbers.containsKey(v)) return v;
                    else return switch (type) {
                            case KlassToken.INT_VARIABLE, KlassToken.LONG_VARIABLE,
                                    KlassToken.FLOAT_VARIABLE, KlassToken.DOUBLE_VARIABLE ->
                                    ReplaceType.replace(type, v);
                            default -> ReplaceType.replaceNumber(v);
                        };
                }).collect(Collectors.toCollection(Stack::new));
    }

    private void addBack(Stack<String> stack, String token) {
        if (stack.isEmpty() || NumberCalculator.getInstance().numbers.containsKey(stack.lastElement())) stack.add(token);
        else stack.add(stack.pop().concat(token));
    }

    private void isSing(Stack<String> stack, String a, StringTokenizer tokenizer) {
        if (!stack.isEmpty()
                && NumberCalculator.getInstance().numbers.containsKey(a)
                && stack.lastElement().endsWith(Token.CALCULATOR_TOKEN)) {
            if (tokenizer.hasMoreTokens()) {
                String b = tokenizer.nextToken();
                if (b.equals(Token.CALCULATOR_TOKEN)) {
                    stack.add(EditToken.bothCut(stack.pop(), 0, Token.CALCULATOR_TOKEN.length()));
                    stack.add(a);
                } else addBack(stack, a.concat(b));
            } else addBack(stack, a);
        } else addBack(stack, a);
    }

    // (*, /, %)
    int indexOf(Stack<Object> stack) {
        ListIterator<Object> it = stack.listIterator();
        while (it.hasNext()) {
            Object token = it.next();
            if (token.equals(Token.MULTIPLY_TOKEN)
                    || token.equals(Token.DIVIDE_TOKEN)
                    || token.equals(Token.REMAIN_TOKEN)) {
                return it.previousIndex();
            }
        }
        return -1;
    }

    Object sum(Object a, Object b) {
        if (a instanceof Integer ai) {
            if (b instanceof Integer bi) return ai + bi;
            else if (b instanceof Long bi) return ai + bi;
            else if (b instanceof Float bi) return ai + bi;
            else if (b instanceof Double bi) return ai + bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Long ai) {
            if (b instanceof Integer bi) return ai + bi;
            else if (b instanceof Long bi) return ai + bi;
            else if (b instanceof Float bi) return ai + bi;
            else if (b instanceof Double bi) return ai + bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Float ai) {
            if (b instanceof Integer bi) return ai + bi;
            else if (b instanceof Long bi) return ai + bi;
            else if (b instanceof Float bi) return ai + bi;
            else if (b instanceof Double bi) return ai + bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Double ai) {
            if (b instanceof Integer bi) return ai + bi;
            else if (b instanceof Long bi) return ai + bi;
            else if (b instanceof Float bi) return ai + bi;
            else if (b instanceof Double bi) return ai + bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else throw VariableException.TYPE_ERROR.getThrow(b);
    }

    Object minus(Object a, Object b) {
        if (a instanceof Integer ai) {
            if (b instanceof Integer bi) return ai - bi;
            else if (b instanceof Long bi) return ai - bi;
            else if (b instanceof Float bi) return ai - bi;
            else if (b instanceof Double bi) return ai - bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Long ai) {
            if (b instanceof Integer bi) return ai - bi;
            else if (b instanceof Long bi) return ai - bi;
            else if (b instanceof Float bi) return ai - bi;
            else if (b instanceof Double bi) return ai - bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Float ai) {
            if (b instanceof Integer bi) return ai - bi;
            else if (b instanceof Long bi) return ai - bi;
            else if (b instanceof Float bi) return ai - bi;
            else if (b instanceof Double bi) return ai - bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Double ai) {
            if (b instanceof Integer bi) return ai - bi;
            else if (b instanceof Long bi) return ai - bi;
            else if (b instanceof Float bi) return ai - bi;
            else if (b instanceof Double bi) return ai - bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else throw VariableException.TYPE_ERROR.getThrow(b);
    }

    Object multiply(Object a, Object b) {
        if (a instanceof Integer ai) {
            if (b instanceof Integer bi) return ai * bi;
            else if (b instanceof Long bi) return ai * bi;
            else if (b instanceof Float bi) return ai * bi;
            else if (b instanceof Double bi) return ai * bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Long ai) {
            if (b instanceof Integer bi) return ai * bi;
            else if (b instanceof Long bi) return ai * bi;
            else if (b instanceof Float bi) return ai * bi;
            else if (b instanceof Double bi) return ai * bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Float ai) {
            if (b instanceof Integer bi) return ai * bi;
            else if (b instanceof Long bi) return ai * bi;
            else if (b instanceof Float bi) return ai * bi;
            else if (b instanceof Double bi) return ai * bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Double ai) {
            if (b instanceof Integer bi) return ai * bi;
            else if (b instanceof Long bi) return ai * bi;
            else if (b instanceof Float bi) return ai * bi;
            else if (b instanceof Double bi) return ai * bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else throw VariableException.TYPE_ERROR.getThrow(b);
    }

    Object divide(Object a, Object b) {
        if (a instanceof Integer ai) {
            if (b instanceof Integer bi) return ai / bi;
            else if (b instanceof Long bi) return ai / bi;
            else if (b instanceof Float bi) return ai / bi;
            else if (b instanceof Double bi) return ai / bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Long ai) {
            if (b instanceof Integer bi) return ai / bi;
            else if (b instanceof Long bi) return ai / bi;
            else if (b instanceof Float bi) return ai / bi;
            else if (b instanceof Double bi) return ai / bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Float ai) {
            if (b instanceof Integer bi) return ai / bi;
            else if (b instanceof Long bi) return ai / bi;
            else if (b instanceof Float bi) return ai / bi;
            else if (b instanceof Double bi) return ai / bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Double ai) {
            if (b instanceof Integer bi) return ai / bi;
            else if (b instanceof Long bi) return ai / bi;
            else if (b instanceof Float bi) return ai / bi;
            else if (b instanceof Double bi) return ai / bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else throw VariableException.TYPE_ERROR.getThrow(b);
    }

    Object remain(Object a, Object b) {
        if (a instanceof Integer ai) {
            if (b instanceof Integer bi) return ai % bi;
            else if (b instanceof Long bi) return ai % bi;
            else if (b instanceof Float bi) return ai % bi;
            else if (b instanceof Double bi) return ai % bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Long ai) {
            if (b instanceof Integer bi) return ai % bi;
            else if (b instanceof Long bi) return ai % bi;
            else if (b instanceof Float bi) return ai % bi;
            else if (b instanceof Double bi) return ai % bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Float ai) {
            if (b instanceof Integer bi) return ai % bi;
            else if (b instanceof Long bi) return ai % bi;
            else if (b instanceof Float bi) return ai % bi;
            else if (b instanceof Double bi) return ai % bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else if (a instanceof Double ai) {
            if (b instanceof Integer bi) return ai % bi;
            else if (b instanceof Long bi) return ai % bi;
            else if (b instanceof Float bi) return ai % bi;
            else if (b instanceof Double bi) return ai % bi;
            else throw VariableException.TYPE_ERROR.getThrow(b);
        } else throw VariableException.TYPE_ERROR.getThrow(b);
    }
}
