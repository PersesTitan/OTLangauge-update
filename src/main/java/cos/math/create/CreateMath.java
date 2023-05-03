package cos.math.create;

import cos.math.etc.MathToken;
import cos.math.etc.MathItem;
import work.CreateWork;

public class CreateMath extends CreateWork<MathItem> {
    public CreateMath() {
        super(MathItem.class, MathToken.MATH);
    }

    @Override
    protected Object createItem(Object[] params) {
        return new MathItem();
    }
}
