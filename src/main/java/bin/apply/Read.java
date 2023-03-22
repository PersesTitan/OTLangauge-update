package bin.apply;

import bin.Repository;
import bin.apply.run.Import;
import bin.repository.code.CodeMap;
import bin.system.loop.For;
import bin.token.CheckToken;
import bin.token.KlassToken;

import java.util.Map;

public class Read {
    public static void read(String path, String repoKlass) {
        CodeMap code = Repository.codes.get(path);
        read(code, 0, code.size() + 1, repoKlass);
    }

    public static void read(CodeMap code, String repoKlass) {
        read(code, 0, code.size() + 1, repoKlass);
    }

    public static void read(String path, int start, int end, String repoKlass) {
        read(Repository.codes.get(path), start, end, repoKlass);
    }

    public static void read(CodeMap code, int start, int end, String repoKlass) {
        final String path = code.getPath();
        // start ~ end 되기 전까지
        for (int i = start + 1; i < end;) i = startLine(code.get(i), path, i, repoKlass);
    }

    public static int startLine(String line, String path, int i, String repoKlass) {
        if (line.isEmpty()) return i + 1;

        if (line.startsWith(KlassToken.IMPORT)) {
            new Import().start(line);
            return i + 1;
        }
        if (CheckToken.startWith(line, '!')) line = line.substring(1);
        else if (Replace.checkToken(line)) line = Replace.replaceToken(line);
        if (For.check(line)) return For.start(line, path, i, repoKlass) + 1;
        else return Start.start(line, path, i, repoKlass);
    }
}
