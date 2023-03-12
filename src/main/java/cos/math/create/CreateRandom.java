package cos.math.create;

import cos.math.etc.MathToken;
import cos.math.etc.RandomItem;
import work.CreateWork;

public class CreateRandom extends CreateWork<RandomItem> {
    public CreateRandom() {
        super(RandomItem.class, MathToken.RANDOM);
    }

    @Override
    protected Object createItem(Object[] params) {
        return new RandomItem();
    }

    @Override
    public void reset() {}
}
