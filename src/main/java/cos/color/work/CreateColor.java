package cos.color.work;

import bin.token.KlassToken;
import cos.color.item.ColorItem;
import cos.color.item.ColorToken;
import work.CreateWork;

public class CreateColor extends CreateWork<ColorItem> {
    public CreateColor() {
        super(
                ColorItem.class, ColorToken.COLOR,
                KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE,
                KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE
        );
    }

    @Override
    protected Object createItem(Object[] params) {
        return new ColorItem((int) params[0], (int) params[1], (int) params[2], (int) params[3]);
    }
}
