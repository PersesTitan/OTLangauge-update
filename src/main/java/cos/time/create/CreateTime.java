package cos.time.create;

import bin.token.KlassToken;
import cos.time.etc.TimeToken;
import cos.time.item.TimeItem;
import work.CreateWork;

public class CreateTime extends CreateWork<TimeItem> {
    public CreateTime() {
        super(TimeItem.class, TimeToken.TIME,
                KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE,
                KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE);
    }

    @Override
    protected Object createItem(Object[] params) {
        return new TimeItem((int) params[0], (int) params[1], (int) params[2], (int) params[3]);
    }

    @Override
    public void reset() {}
}
