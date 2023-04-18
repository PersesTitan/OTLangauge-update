package cos.file.etc;

import bin.token.KlassToken;
import bin.token.SeparatorToken;
import bin.token.Token;
import cos.file.item.FileItem;
import work.CreateWork;

public class CreateFile extends CreateWork<FileItem> {
    public CreateFile() {
        super(FileItem.class, FileToken.FILE, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object createItem(Object[] params) {
        String filePath = params[0].toString().replace(Token.ACCESS, SeparatorToken.SEPARATOR_FILE);
        return new FileItem(filePath);
    }
}
