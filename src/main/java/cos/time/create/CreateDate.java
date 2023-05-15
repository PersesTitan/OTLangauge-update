package cos.time.create;

import bin.token.KlassToken;
import cos.time.etc.TimeToken;
import cos.time.item.DateItem;
import work.CreateWork;

public class CreateDate extends CreateWork<DateItem> {
    public CreateDate() {
        super(DateItem.class, TimeToken.DATE,
                KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE);
    }

    @Override
    protected Object createItem(Object[] params) {
        return new DateItem((int) params[0], (int) params[1], (int) params[2]);
    }

    @Override
    public void reset() {}
}
