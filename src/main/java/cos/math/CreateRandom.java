package cos.math;

import bin.token.KlassToken;
import work.CreateWork;

import java.util.Random;

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
