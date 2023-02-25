package cos.math.work.random;

import cos.math.MathToken;
import cos.math.RandomItem;
import work.ReplaceWork;

import java.util.Random;

public class RandomInteger extends ReplaceWork {
    public RandomInteger() {
        super(MathToken.RANDOM);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return ((RandomItem) klassValue).nextInt();
    }

    @Override
    public void reset() {}
}
