import bin.apply.mode.LoopMode;
import bin.token.EditToken;
import bin.token.Token;
import cos.math.RandomItem;

import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        RandomItem random = new RandomItem();
//        System.out.println((int) 'A');
//        System.out.println((int) 'z');
//        System.out.println((int) '0');
//        System.out.println((int) '9');

        String[][] str = {{"ㅇㅁㅇ", "변수명"}, {"ㅇㅈㅇ", "변수명2"}};
        int len = str[0].length;
        String[] paramType = new String[len];
        for (int i = 0; i < len; i++) paramType[i] = str[i][0];
        System.out.println(Arrays.toString(paramType));

//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i<50; i++) {
//            builder.append((char) random.nextInt());
//        }
//        System.out.println(builder);
    }
}
