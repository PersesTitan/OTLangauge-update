import bin.Repository;
import bin.apply.Read;
import bin.apply.mode.RunMode;
import bin.exception.Error;
import bin.exception.FileException;
import bin.repository.code.CodeMap;
import bin.token.KlassToken;
import bin.token.SeparatorToken;
import bin.token.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            new Main(args);
        } catch (Error ignored) {}
    }

    private Main(String[] args) {
        if (SeparatorToken.isWindow) {
            switch (args.length) {
                case 0 -> RunMode.SHELL.set();
                case 1 -> RunMode.NORMAL.set();
                default -> throw FileException.VALID_VALUES_ERROR.getThrow(null);
            }
            try {System.in.read();}
            catch (IOException ignored) {}
        } else {
            switch (args.length) {
                case 0 -> RunMode.SHELL.set();
                case 1 -> RunMode.NORMAL.set();
                default -> throw FileException.VALID_VALUES_ERROR.getThrow(null);
            }
            switch (RunMode.getMode()) {
                case SHELL -> this.shell();
                case NORMAL -> this.normal(args[0]);
            }
        }
    }

    private void normal(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) throw FileException.DO_NOT_PATH.getThrow(filePath);
        else if (!file.isFile()) throw FileException.FILE_TYPE_ERROR.getThrow(filePath);
        else if (!file.canRead()) throw FileException.DO_NOT_READ.getThrow(filePath);
        if (SeparatorToken.isExtension(file.getName())) {
            // 파일 읽기
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String path = file.getPath();
                CodeMap code = new CodeMap(path);
                for (int i=1;;i++) {
                    String line = reader.readLine();
                    if (line == null) break;
                    code.put(i, line);
                }

                Repository.codes.put(path, code);
                Read.read(code, KlassToken.SYSTEM);
            } catch (IOException e) {
                throw FileException.DO_NOT_READ.getThrow(filePath);
            }
        } else throw FileException.EXTENSION_MATCH_ERROR.getThrow(file.getName());
    }

    private void shell() {
        Scanner scanner = new Scanner(System.in);
        CodeMap code = new CodeMap("");
        Repository.codes.put("", code);
        int i = 1;

        while (true) {
            System.out.print(">>> ");
            String line = scanner.nextLine().strip();
            if (line.equals(KlassToken.QUIT)) break;
            code.put(i++, line);
            if (line.endsWith(Token.LOOP_S)) {
                int stack = 1;
                int start = i - 1;
                do {
                    System.out.print("--- ");
                    code.put(i++, line = scanner.nextLine().strip());
                    if (line.startsWith(Token.LOOP_E)) stack--;
                    else if (line.endsWith(Token.LOOP_S)) stack++;
                } while (stack > 0);
                i = shellLine(code.get(start), i);
            } else shellLine(line, i);
        }
    }

    private int shellLine(String line, int i) {
        try {
            return Read.startLine(line, "", i, KlassToken.SYSTEM);
        } catch (Error e) {
            e.print("", line, i);
            throw e;
        }
    }
}
