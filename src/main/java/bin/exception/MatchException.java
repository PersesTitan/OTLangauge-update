package bin.exception;

import bin.apply.calculator.number.NumberCalculator;
import bin.token.SeparatorToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@RequiredArgsConstructor
public enum MatchException implements ExceptionTool {
    SYSTEM_ERROR("시스템 에러가 발생하였습니다."),
    GRAMMAR_ERROR("문법이 일치하지 않습니다."),
    PARAM_COUNT_ERROR("파라미터 길이가 유효하지 않습니다."),
    ZONE_MATCH_ERROR("괄호가 일치하지 않아 존을 사용할 수 없습니다."),
    DEFINE_CLASS_NAME("이미 정의된 클래스 이름입니다."),
    DEFINE_METHOD_NAME("이미 정의된 메소드 이름입니다."),
    CALCULATOR_ERROR("계산식 중 유효하지 않는 토큰이 존재합니다."),
    SING_NUMBER_ERROR("숫자 계산에 유효하지 않는 토큰입니다."),
    PARAM_MATCH_ERROR("파라미터 문법이 일치하지 않습니다."),
    MAP_MATCH_ERROR("맵에서는 =를 사용하여 값을 구분해주어야합니다."),
    CREATE_KLASS_ERROR("해당 위치에서 클래스를 생성할 수 없습니다."),
    CREATE_METHOD_ERROR("해당 위치에서 메소드를 생성 할 수 없습니다.")
    ;

    private AtomicReference<String> errorCode;
    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case SYSTEM_ERROR ->
                    """
                    A system error has occurred. %s
                    A problem has occurred that should not have occurred.
                    Please contact the developer.
                    """;
            case GRAMMAR_ERROR ->
                    """
                    The grammar does not match. %s
                    Please check the grammar.
                    """;
            case PARAM_COUNT_ERROR ->
                    """
                    Parameter length is invalid. %s
                    Please check the parameter length.
                    """;
            case ZONE_MATCH_ERROR ->
                    """
                    Zone is not available because parentheses do not match. %s
                    Please check the grammar.
                    """;
            case DEFINE_CLASS_NAME ->
                    """
                    The class name is already defined. %s
                    Please check the name.
                    """;
            case DEFINE_METHOD_NAME ->
                    """
                    The method name is already defined. %s
                    Please check the name.
                    """;
            case CALCULATOR_ERROR ->
                    """
                    An invalid token exists in the calculation expression. %s
                    Please check the calculation formula.
                    """;
            case PARAM_MATCH_ERROR ->
                    """
                    Parameter grammar does not match. %s
                    Please check the grammar again.
                    """;
            case MAP_MATCH_ERROR ->
                    """
                    In maps, you must use = to separate values. %s
                    Please check the grammar again.
                    """;
            case CREATE_KLASS_ERROR ->
                    """
                    Cannot create a class from that location. %s
                    Please check the grammar again.
                    """;
            case CREATE_METHOD_ERROR ->
                    """
                    Cannot create a method from that location. %s
                    Please check the grammar again.
                    """;
            case SING_NUMBER_ERROR ->
                    "Please check the grammar again.Invalid token for numeric calculation. %s" +
                    SeparatorToken.SEPARATOR_LINE +
                    "Token : (" + String.join(", ", NumberCalculator.getInstance().numbers.keySet()) + ")" +
                    SeparatorToken.SEPARATOR_LINE +
                    "Please check the grammar again.";
        };
    }

    @Override
    public Error getThrow(String errorCode) {
        if (this.errorCode == null) this.errorCode = new AtomicReference<>(errorCode);
        else this.errorCode.set(errorCode);
        return new Error(this.name(), this.getClass());
    }
}
