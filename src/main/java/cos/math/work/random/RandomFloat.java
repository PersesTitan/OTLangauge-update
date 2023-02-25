package cos.math.work.random;

import cos.math.MathToken;
import cos.math.RandomItem;
import work.ReplaceWork;

public class RandomFloat extends ReplaceWork {
    public RandomFloat() {
        super(MathToken.RANDOM);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return ((RandomItem) klassValue).nextFloat();
    }

    @Override
    public void reset() {

    }
}
