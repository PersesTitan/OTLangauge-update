package cos.math;

import bin.Repository;
import cos.math.create.CreateMath;
import cos.math.etc.MathItem;
import cos.math.etc.MathToken;
import work.ResetWork;

public class Reset implements ResetWork, MathToken {
    @Override
    public void reset() {
        Repository.createWorks.put(MATH, new CreateMath());

        AddWork<MathItem> addWork = new AddWork<>(MATH);

        addWork.addR(b, b, MathItem::nextBoolean);

        addWork.addR(i, RANDOM_INTEGER, MathItem::nextInt);
        addWork.addR(i, BOUND_INTEGER, i, MathItem::boundInteger);
        addWork.addR(i, ORIGIN_INTEGER, i, i, MathItem::originInteger);

        addWork.addR(l, RANDOM_LONG, MathItem::nextLong);
        addWork.addR(l, BOUND_LONG, l, MathItem::boundLong);
        addWork.addR(l, ORIGIN_LONG, l, l, MathItem::originLong);

        addWork.addR(f, RANDOM_FLOAT, MathItem::nextFloat);
        addWork.addR(f, BOUND_FLOAT, f, MathItem::boundFloat);
        addWork.addR(f, ORIGIN_FLOAT, f, f, MathItem::originFloat);

        addWork.addR(d, RANDOM_DOUBLE, MathItem::nextDouble);
        addWork.addR(d, BOUND_DOUBLE, d, MathItem::boundDouble);
        addWork.addR(d, ORIGIN_DOUBLE, d, d, MathItem::originDouble);

        addWork.addSR(d, SIN, d, Math::sin);
        addWork.addSR(d, COS, d, Math::cos);
        addWork.addSR(d, TAN, d, Math::tan);

        addWork.addSR(d, CEIL, d, Math::ceil);
        addWork.addSR(d, FLOOR, d, Math::floor);
        addWork.addSR(i, ROUND_FLOAT, f, MathItem::roundFloat);
        addWork.addSR(l, ROUND_DOUBLE, d, MathItem::roundDouble);

        addWork.addSR(d, POW, d, d, Math::pow);
        addWork.addSR(d, SQRT, d, Math::sqrt);
        addWork.addSR(d, EXP, d, Math::exp);
        addWork.addSR(d, PI, () -> Math.PI);
        addWork.addSR(d, E, () -> Math.E);

        addWork.addSR(i, ABS_INTEGER, i, MathItem::absI);
        addWork.addSR(l, ABS_LONG, l, MathItem::absL);
        addWork.addSR(f, ABS_FLOAT, f, MathItem::absF);
        addWork.addSR(d, ABS_DOUBLE, d, MathItem::absD);
    }
}