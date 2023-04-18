package cos.color.work;

import bin.token.KlassToken;
import cos.color.item.ColorItem;
import cos.color.item.ColorToken;
import work.StartWork;

public class SetColor extends StartWork {
    public SetColor() {
        super(
                ColorToken.COLOR, false,
                KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE,
                KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE
        );
    }

    @Override
    protected void startItem(Object klassValue, Object[] params) {
        ((ColorItem) klassValue).setColor((int) params[0], (int) params[1], (int) params[2], (int) params[3]);
    }
}
