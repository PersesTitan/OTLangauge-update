package cos.math.work.random;

import cos.math.MathToken;
import cos.math.RandomItem;
import work.ReplaceWork;

public class RandomDouble extends ReplaceWork {
    public RandomDouble() {
        super(MathToken.RANDOM);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return ((RandomItem) klassValue).nextDouble();
    }

    @Override
    public void reset() {

    }
}
