package cos.math;

import bin.token.KlassToken;

import java.util.Random;
import java.util.UUID;

public class RandomItem extends Random {
    public char nextCharacter() {
        return (char) this.nextInt();
    }

    public String nextString() {
        return this.nextString(16);
    }

    public String nextString(int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) builder.append((char) this.nextInt(44032, 55204));
        return builder.toString();
    }

    @Override
    public String toString() {
        return MathToken.RANDOM;
    }
}
