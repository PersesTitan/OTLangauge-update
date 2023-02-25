package bin.apply.mode;

import bin.Repository;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.Token;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

@RequiredArgsConstructor
public enum LoopMode {
    NONE    (line -> line.equals(Token.LOOP_E), null),
    PUT     (line -> {
        int i = line.indexOf(Token.PUT_TOKEN);
        return line.startsWith(Token.LOOP_E)
                && i != -1
                && line.substring(1, i).isBlank()
                && !line.substring(i + Token.PUT_TOKEN.length()).isBlank();
        // } <= ㅇㅁㅇ 변수명
        }, line -> line.substring(line.indexOf(Token.PUT_TOKEN) + Token.PUT_TOKEN.length()).strip()),
    RETURN  (line -> {
        int i = line.indexOf(Token.RETURN_TOKEN);
        return line.startsWith(Token.LOOP_E)
                && i != -1
                && line.substring(1, i).isBlank()
                && !line.substring(i + Token.RETURN_TOKEN.length()).isBlank();
        // } => 변수명
        }, line -> line.substring(line.indexOf(Token.RETURN_TOKEN) + Token.RETURN_TOKEN.length()).strip()),
    OTHER   (line -> true, null);

    private final Function<String, Boolean> check;
    private final Function<String, String> casting;

    public static LoopMode getMode(String line) {
        for (LoopMode mode : LoopMode.values()) {
            if (mode.check(line)) return mode;
        }
        return LoopMode.OTHER;
    }

    public static int next(String path, int start) {
        return next(Repository.codes.get(path), start);
    }

    public static int next(Map<Integer, String> code, int start) {
        int count = 0;
        int size = code.size() + 1;
        for (int i = start + 1; i < size; i++) {
            String line = code.get(i);
            if (line.isEmpty()) continue;
            if (line.startsWith(Token.LOOP_E)) {
                if (count == 0) return i;
                else count--;
            } else if (line.endsWith(Token.LOOP_S)) count++;
        }
        throw MatchException.ZONE_MATCH_ERROR.getThrow(code.get(start));
    }

    public boolean check(String line) {
        return this.check.apply(line);
    }

    // } <= ㅇㅁㅇ 변수명 -> ㅇㅁㅇ 변수명
    // } => 변수명      -> 변수명
    public String getToken(String line) {
        if (this.casting == null) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        else return this.casting.apply(line);
    }
}
