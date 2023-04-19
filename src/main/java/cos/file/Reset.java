package cos.file;

import bin.Repository;
import bin.token.KlassToken;
import cos.file.etc.CreateFile;
import cos.file.etc.FileToken;
import cos.file.item.FileItem;
import work.ResetWork;

public class Reset implements ResetWork, KlassToken, FileToken {
    @Override
    public void reset() {
        Repository.createWorks.put(FILE, new CreateFile());

        AddWork<FileItem> addWork = new AddWork<>(FILE);

        addWork.addR(LIST_STRING, FILE_LIST, FileItem::fileList);
        addWork.addR(LIST_STRING, FILE_READ, FileItem::readFile);
        addWork.addR(b, IS_FILE, FileItem::isFile);
        addWork.addR(b, IS_DIRECTORY, FileItem::isDirectory);
    }
}
