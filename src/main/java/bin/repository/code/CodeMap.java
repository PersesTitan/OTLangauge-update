package bin.repository.code;

import bin.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public class CodeMap extends HashMap<Integer, String> {
    private final String path;

    @Override
    public String put(Integer key, String value) {
        return super.put(key, (value = value.strip()).startsWith(Token.REMARK) ? "" : value);
    }
}
