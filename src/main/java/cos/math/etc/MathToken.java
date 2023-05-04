package cos.math.etc;

public interface MathToken {
    String MATH = "ㅅㅎㅅ";

    String SIN = "ㅅㅆㅅ";
    String COS = "ㅋㅆㅋ";
    String TAN = "ㅌㅆㅌ";

    String ABS = "ㅈㄷㅈ";

    String ABS_INTEGER  = "ㅈㄷㅈ";
    String ABS_LONG     = "ㅉㄷㅉ";
    String ABS_FLOAT    = "ㅅㄷㅅ";
    String ABS_DOUBLE   = "ㅆㄷㅆ";

    String CEIL     = "ㅇㄹㅇ";   // 올림
    String FLOOR    = "ㅂㄹㅂ";   // 버림
    String ROUND_FLOAT    = "ㄴㅅㄴ";  // 반올림 (float)
    String ROUND_DOUBLE   = "ㄴㅆㄴ";  // 반올림 (double)

    String POW = "ㅈㄱㅈ";    // 제곱 연산 (5,2 => 25)
    String SQRT = "ㅈㄴㅈ";   // 제곱근 (25 => 5)
    String EXP = "ㄹㄱㄹ";   // E 제곱근

    String E = ">ㄹㄱㄹ";
    String PI = ">ㅍㅇㅍ";

    String RANDOM_INTEGER   = "@ㅈ@";
    String RANDOM_LONG      = "@ㅉ@";
    String RANDOM_BOOLEAN   = "@ㅂ@";
    String RANDOM_STRING    = "@ㅁ@";
    String RANDOM_CHARACTER = "@ㄱ@";
    String RANDOM_FLOAT     = "@ㅅ@";
    String RANDOM_DOUBLE    = "@ㅆ@";

    String BOUND_INTEGER   = "@즈@";
    String BOUND_LONG      = "@쯔@";
    String BOUND_BOOLEAN   = "@브@";
    String BOUND_STRING    = "@므@";
    String BOUND_CHARACTER = "@그@";
    String BOUND_FLOAT     = "@스@";
    String BOUND_DOUBLE    = "@쓰@";

    String ORIGIN_INTEGER   = "@주@";
    String ORIGIN_LONG      = "@쭈@";
    String ORIGIN_BOOLEAN   = "@부@";
    String ORIGIN_STRING    = "@무@";
    String ORIGIN_CHARACTER = "@구@";
    String ORIGIN_FLOAT     = "@수@";
    String ORIGIN_DOUBLE    = "@쑤@";
}
