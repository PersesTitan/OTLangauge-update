package cos.math.work.random;

import cos.math.MathToken;
import cos.math.RandomItem;
import work.ReplaceWork;

import java.util.Random;

public class RandomBoolean extends ReplaceWork {
    public RandomBoolean() {
        super(MathToken.RANDOM);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return ((RandomItem) klassValue).nextBoolean();
    }

    @Override
    public void reset() {

    }
}
