package bin.apply;

import bin.Repository;

import java.util.Map;

public class Read {
    public static void read(String path) {
        Map<Integer, String> code = Repository.codes.get(path);
        read(code, 1, code.size() + 1);
    }

    public static void read(Map<Integer, String> code) {
        read(code, 1, code.size() + 1);
    }

    public static void read(String path, int start, int end) {
        read(Repository.codes.get(path), start, end);
    }

    public static void read(Map<Integer, String> code, int start, int end) {
        // start ~ end 되기 전까지
        for (int i = start; i < end; i++) {
            String line = code.get(i);
            if (line.isEmpty()) continue;
            Start.start(line);
        }
    }
}
