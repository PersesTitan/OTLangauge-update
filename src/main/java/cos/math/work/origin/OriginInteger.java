package cos.math.work.origin;

import bin.token.KlassToken;
import cos.math.MathToken;
import cos.math.RandomItem;
import work.ReplaceWork;

import java.util.Random;

public class OriginInteger extends ReplaceWork {
    public OriginInteger() {
        super(MathToken.RANDOM, KlassToken.INT_VARIABLE, KlassToken.INT_VARIABLE);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return ((RandomItem) klassValue).nextInt((int) params[0], (int) params[1]);
    }

    @Override
    public void reset() {

    }
}
