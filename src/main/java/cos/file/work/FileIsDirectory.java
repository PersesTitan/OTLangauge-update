package cos.file.work;

import bin.token.KlassToken;
import cos.file.etc.FileToken;
import cos.file.item.FileItem;
import work.ReplaceWork;

public class FileIsDirectory extends ReplaceWork {
    public FileIsDirectory() {
        super(FileToken.FILE, KlassToken.BOOL_VARIABLE, false);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return ((FileItem) klassValue).isDirectory();
    }
}
