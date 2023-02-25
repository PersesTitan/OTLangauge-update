package cos.math.work.random;

import cos.math.MathToken;
import cos.math.RandomItem;
import work.ReplaceWork;

public class RandomLong extends ReplaceWork {
    public RandomLong() {
        super(MathToken.RANDOM);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return ((RandomItem) klassValue).nextLong();
    }

    @Override
    public void reset() {

    }
}
