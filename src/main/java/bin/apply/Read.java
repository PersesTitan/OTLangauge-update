package bin.apply;

import bin.Repository;
import bin.repository.code.CodeMap;
import bin.system.loop.For;

import java.util.Map;

public class Read {
    public static void read(String path) {
        CodeMap code = Repository.codes.get(path);
        read(code, 1, code.size() + 1);
    }

    public static void read(CodeMap code) {
        read(code, 1, code.size() + 1);
    }

    public static void read(String path, int start, int end) {
        read(Repository.codes.get(path), start, end);
    }

    public static void read(CodeMap code, int start, int end) {
        final String path = code.getPath();
        // start ~ end 되기 전까지
        for (int i = start; i < end;) i = startLine(code.get(i), path, i);
    }

    public static int startLine(String line, String path, int i) {
        if (line.isEmpty()) return i + 1;
        if (Replace.checkToken(line)) line = Replace.replaceToken(line);
        if (For.check(line)) return For.start(line, path, i);
        else return Start.start(line, path, i, null);
    }
}
