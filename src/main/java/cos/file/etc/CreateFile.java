package cos.file.etc;

import cos.file.item.FileItem;
import work.CreateWork;

import java.io.File;

public class CreateFile extends CreateWork<FileItem> {
    public CreateFile(String klassName, String... params) {
        super(FileItem.class, klassName, params);
    }

    @Override
    protected Object createItem(Object[] params) {
        return null;
    }

    @Override
    public void reset() {}
}
