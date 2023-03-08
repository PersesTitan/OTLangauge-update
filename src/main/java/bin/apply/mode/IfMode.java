package bin.apply.mode;

import bin.exception.MatchException;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.Token;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public enum IfMode {
    IF      (Token.IF,      line -> {
        if (line.startsWith(Token.IF)) {
            if (CheckToken.endWith(line, Token.LOOP_SC)) {
                if (EditToken.bothCut(line, Token.IF.length(), 1).isBlank()) {
                    throw MatchException.GRAMMAR_ERROR.getThrow(line);
                } else return true;
            } else throw MatchException.ZONE_MATCH_ERROR.getThrow(line);
        } else return false;
    }),
    ELSE_IF (Token.ELSE_IF, line -> {
        if (line.startsWith(Token.ELSE_IF)) {
            if (CheckToken.endWith(line, Token.LOOP_SC)) {
                if (EditToken.bothCut(line, Token.ELSE_IF.length(), 1).isBlank()) {
                    throw MatchException.GRAMMAR_ERROR.getThrow(line);
                } else return true;
            } else throw MatchException.ZONE_MATCH_ERROR.getThrow(line);
        } else return false;
    }),
    ELSE    (Token.ELSE,    line -> {
        if (line.startsWith(Token.ELSE)) {
            if (CheckToken.endWith(line, Token.LOOP_SC)) {
                if (EditToken.bothCut(line, Token.ELSE.length(), 1).isBlank()) {
                    return true;
                } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
            } else throw MatchException.ZONE_MATCH_ERROR.getThrow(line);
        } else return false;
    });

    private final String token;
    private final Function<String, Boolean> function;

    public boolean check(String line) {
        return this.function.apply(line);
    }
}
