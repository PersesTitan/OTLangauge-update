package cos.file.item;

import java.io.File;

public class FileItem {
    private final File file;
    public FileItem(String filePath) {
        this.file = new File(filePath);
    }
}
