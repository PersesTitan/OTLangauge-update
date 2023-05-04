package cos.math.etc;

import java.util.Random;

public class MathItem extends Random {
//    public char nextCharacter() {
//        return (char) this.nextInt();
//    }
//
//    public String nextString() {
//        return this.nextString(16);
//    }
//
//    public String nextString(int count) {
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < count; i++) builder.append((char) this.nextInt(44032, 55204));
//        return builder.toString();
//    }

    public static int roundFloat(float v) {
        return Math.round(v);
    }

    public static long roundDouble(double v) {
        return Math.round(v);
    }

    public static int absI(int i) {
        return Math.abs(i);
    }

    public static long absL(long l) {
        return Math.abs(l);
    }

    public static float absF(float f) {
        return Math.abs(f);
    }

    public static double absD(double d) {
        return Math.abs(d);
    }

    public int boundInteger(int i) {
        if (i <= 0) throw MathException.RANDOM_BOUND_ERROR.getThrow(i);
        return super.nextInt(i);
    }

    public int originInteger(int a, int b) {
        return super.nextInt(a, b);
    }

    public long boundLong(long l) {
        if (l <= 0) throw MathException.RANDOM_BOUND_ERROR.getThrow(l);
        return super.nextLong(l);
    }

    public long originLong(long a, long b) {
        return super.nextLong(a, b);
    }

    public float boundFloat(float a) {
        if (a <= 0) throw MathException.RANDOM_BOUND_ERROR.getThrow(a);
        return super.nextFloat(a);
    }

    public float originFloat(float a, float b) {
        return super.nextFloat(a, b);
    }

    public double boundDouble(double a) {
        if (a <= 0) throw MathException.RANDOM_BOUND_ERROR.getThrow(a);
        return super.nextDouble(a);
    }

    public double originDouble(double a, double b) {
        return super.nextDouble(a, b);
    }

    @Override
    public String toString() {
        return MathToken.MATH;
    }
}
