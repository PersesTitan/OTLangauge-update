package cos.internet;

import bin.Repository;
import bin.token.KlassToken;
import cos.internet.etc.InternetToken;
import cos.internet.item.InternetItem;
import cos.internet.work.CreateInternet;
import work.ResetWork;

public class Reset implements ResetWork, InternetToken {
    @Override
    public void reset() {
        Repository.createWorks.put(INTERNET, new CreateInternet());

        AddWork<InternetItem> addWork = new AddWork<>(INTERNET);

        addWork.addR(KlassToken.LIST_STRING, INTERNET_READER, InternetItem::reader);
    }
}
