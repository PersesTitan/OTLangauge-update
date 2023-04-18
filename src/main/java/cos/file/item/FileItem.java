package cos.file.item;

import bin.exception.FileException;
import bin.token.SeparatorToken;
import bin.token.Token;
import bin.variable.Types;
import bin.variable.custom.CustomList;
import cos.file.etc.FileToken;
import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

@Getter
public class FileItem {
    private final File file;
    private final String filePath;

    public FileItem(String filePath) {
        this(new File(filePath));
    }

    public FileItem(File file) {
        // filePath : file/dir/test.otl
        this.file = file;
        // filePath : file~dir~test.otl
        this.filePath = this.file.getPath().replace(SeparatorToken.SEPARATOR_FILE, Token.ACCESS);
    }

    public CustomList<Object> fileList() {
        CustomList<Object> list = new CustomList<>(Types.STRING);
        File[] files = this.file.listFiles();
        if (files == null) return list;
        Stream.of(files)
                .map(File::getPath)
                .map(v -> v.replace(SeparatorToken.SEPARATOR_FILE, Token.ACCESS))
                .map(v -> FileToken.FILE + Token.PARAM_S + v + Token.PARAM_E)
                .forEach(list::add);
        return list;
    }

    public CustomList<String> readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.file, StandardCharsets.UTF_8))) {
            return new CustomList<>(Types.STRING, reader.lines().toList());
        } catch (IOException e) {
            throw FileException.DO_NOT_READ.getThrow(this);
        }
    }

    public void writeFile(Collection<String> collection) {
        Iterator<String> iterator = collection.iterator();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file))) {
            while (iterator.hasNext()) {
                writer.write(iterator.next());
                if (iterator.hasNext()) writer.newLine();
            }
        } catch (IOException e) {
            throw FileException.CREATE_FILE_ERROR.getThrow(this.file);
        }
    }

    public boolean isFile() {
        return this.file.isFile();
    }

    public boolean isDirectory() {
        return this.file.isDirectory();
    }

    public boolean exists() {
        return this.file.exists();
    }

    public String getName() {
        return this.file.getName();
    }

    public String getPath() {
        return this.file.getPath();
    }

    public String getAbsolutePath() {
        return this.file.getAbsolutePath();
    }

    @Override
    public String toString() {
        return FileToken.FILE + Token.PARAM_S + this.filePath + Token.PARAM_E;
    }
}
