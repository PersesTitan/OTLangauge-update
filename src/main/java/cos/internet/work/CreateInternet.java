package cos.internet.work;

import bin.token.KlassToken;
import cos.internet.etc.InternetToken;
import cos.internet.item.InternetItem;
import work.CreateWork;

public class CreateInternet extends CreateWork<InternetItem> {
    public CreateInternet() {
        super(InternetItem.class, InternetToken.INTERNET, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object createItem(Object[] params) {
        return new InternetItem(params[0].toString());
    }
}
