package cos.math.work.bound;

import bin.token.KlassToken;
import cos.math.MathToken;
import cos.math.RandomItem;
import cos.math.exception.MathException;
import work.ReplaceWork;

import java.util.Random;

public class BoundInteger extends ReplaceWork {
    public BoundInteger() {
        super(MathToken.RANDOM, KlassToken.INT_VARIABLE);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        int i = (int) params[0];
        if (i <= 0) throw MathException.RANDOM_BOUND_ERROR.getThrow(params[0].toString());
        return ((RandomItem) klassValue).nextInt(i);
    }

    @Override
    public void reset() {}
}
