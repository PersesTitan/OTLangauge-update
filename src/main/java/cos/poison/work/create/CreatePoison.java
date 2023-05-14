package cos.poison.work.create;

import cos.poison.etc.PoisonToken;
import cos.poison.controller.HttpServerManager;
import cos.poison.item.PoisonItem;
import work.CreateWork;

public class CreatePoison extends CreateWork<PoisonItem> {
    public CreatePoison() {
        super(PoisonItem.class, PoisonToken.POISON);
    }

    @Override
    protected Object createItem(Object[] params) {
        return new PoisonItem();
    }
}
