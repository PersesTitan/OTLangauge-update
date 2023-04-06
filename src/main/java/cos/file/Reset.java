package cos.file;

import bin.Repository;
import bin.token.KlassToken;
import cos.file.etc.CreateFile;
import cos.file.etc.FileToken;
import cos.file.item.FileItem;
import cos.file.work.FileIsDirectory;
import cos.file.work.FileIsFile;
import cos.file.work.FileList;
import cos.file.work.FileRead;
import work.ReplaceWork;
import work.ResetWork;

public class Reset implements ResetWork, KlassToken, FileToken {
    @Override
    public void reset() {
        Repository.createWorks.put(FILE, new CreateFile());

        Repository.replaceWorks.put(LIST_STRING, FILE, FILE_LIST, new FileList());
        Repository.replaceWorks.put(LIST_STRING, FILE, FILE_READ, new FileRead());
        Repository.replaceWorks.put(BOOL_VARIABLE, FILE, IS_FILE, new FileIsFile());
        Repository.replaceWorks.put(BOOL_VARIABLE, FILE, IS_DIRECTORY, new FileIsDirectory());
    }
}
