package bin.system.loop;

import bin.Repository;
import bin.apply.Read;
import bin.apply.Replace;
import bin.apply.loop.Loop;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.code.CodeMap;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;
import bin.variable.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class For {
    public static boolean check(String line) {
        int start, end;
        return line.endsWith(Token.LOOP_S)
                && (start = line.indexOf(Token.FOR)) >= 0
                && (end = line.lastIndexOf(Token.FOR)) >= 0
                && start != end;
    }

    public static int start(String line, String path, int start, String repoKlass) {
        CodeMap code = Repository.codes.get(path);
        int end = LoopMode.next(code, start);

        String token1, token2, token3;
        if (!line.endsWith(Token.LOOP_S)) throw MatchException.ZONE_MATCH_ERROR.getThrow(line);
        StringTokenizer tokenizer = new StringTokenizer(line = EditToken.bothCut(line, 0, 1), Token.FOR_STR);
        if (tokenizer.countTokens() == 3) {
            token1 = tokenizer.nextToken().strip();
            token2 = tokenizer.nextToken().strip();
            token3 = tokenizer.nextToken().strip();
        } else throw MatchException.GRAMMAR_ERROR.getThrow(line);

        String endLine = code.get(end);
        switch (LoopMode.getMode(endLine)) {
            // } <= ㅇㅁㅇ 변수명
            case PUT -> {
                // putLine : ㅇㅁㅇ 변수명
                String[] putLines = LoopMode.PUT.getToken(endLine).split("\\s", 2);
                String klassType = putLines[0].strip();
                String variableName = putLines[1].strip();
                switch (klassType) {
                    case KlassToken.INT_VARIABLE -> {
                        int a = (int) Types.INTEGER.originCast(token1);
                        int b = (int) Types.INTEGER.originCast(token2);
                        int c = (int) Types.INTEGER.originCast(token3);
                        List<Integer> list = new ArrayList<>();
                        for (int i = a; i < b; i+=c) list.add(i);
                        Loop.PUT(klassType, variableName, list, () -> Read.read(code, start, end, repoKlass));
                    }
                    case KlassToken.LONG_VARIABLE -> {
                        long a = (long) Types.LONG.originCast(token1);
                        long b = (long) Types.LONG.originCast(token2);
                        long c = (long) Types.LONG.originCast(token3);
                        List<Long> list = new ArrayList<>();
                        for (long i = a; i < b; i+=c) list.add(i);
                        Loop.PUT(klassType, variableName, list, () -> Read.read(code, start, end, repoKlass));
                    }
                    case KlassToken.FLOAT_VARIABLE -> {
                        float a = (float) Types.FLOAT.originCast(token1);
                        float b = (float) Types.FLOAT.originCast(token2);
                        float c = (float) Types.FLOAT.originCast(token3);
                        List<Float> list = new ArrayList<>();
                        for (float i = a; i < b; i+=c) list.add(i);
                        Loop.PUT(klassType, variableName, list, () -> Read.read(code, start, end, repoKlass));
                    }
                    case KlassToken.DOUBLE_VARIABLE -> {
                        double a = (double) Types.DOUBLE.originCast(token1);
                        double b = (double) Types.DOUBLE.originCast(token2);
                        double c = (double) Types.DOUBLE.originCast(token3);
                        List<Double> list = new ArrayList<>();
                        for (double i = a; i < b; i+=c) list.add(i);
                        Loop.PUT(klassType, variableName, list, () -> Read.read(code, start, end, repoKlass));
                    }
                    default -> throw VariableException.TYPE_ERROR.getThrow(klassType);
                }
            }
            // }
            case NONE -> {
                double a = getNumber(token1);
                double b = getNumber(token2);
                double c = getNumber(token3);
                for (double i = a; i < b; i+=c) Read.read(code, start, end, repoKlass);
            }
            default -> throw MatchException.ZONE_MATCH_ERROR.getThrow(endLine);
        }

        return end;
    }

    private static double getNumber(String line) {
        Object o = Replace.replace(line);
        if (o instanceof Integer i) return (double) i;
        else if (o instanceof Character c) return (double) c;
        else if (o instanceof Long l) return (double) l;
        else if (o instanceof Float f) return (double) f;
        else if (o instanceof Double d) return d;
        else throw VariableException.TYPE_ERROR.getThrow(o);
    }
}
