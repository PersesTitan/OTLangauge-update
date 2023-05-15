package cos.time.create;

import cos.time.etc.TimeToken;
import cos.time.item.DateItem;
import cos.time.item.DateTimeItem;
import cos.time.item.TimeItem;
import work.CreateWork;

public class CreateDateTime extends CreateWork<DateTimeItem> {
    public CreateDateTime() {
        super(DateTimeItem.class, TimeToken.DATE_TIME, TimeToken.DATE, TimeToken.TIME);
    }

    @Override
    protected Object createItem(Object[] params) {
        return new DateTimeItem((DateItem) params[0], (TimeItem) params[1]);
    }

    @Override
    public void reset() {}
}
