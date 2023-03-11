package bin.exception;

import bin.token.KlassToken;
import bin.token.SeparatorToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

import static bin.token.Token.NO_USE;

@Getter
@RequiredArgsConstructor
public enum VariableException implements ExceptionTool {
    VALUE_ERROR("변수 값이 유효하지 않습니다."),
    TYPE_ERROR("변수 타입이 유효하지 않습니다."),
    NO_DEFINE_NAME("정의되지 않은 변수명입니다."),
    NO_DEFINE_TYPE("정의되지 않은 변수타입입니다."),
    DEFINE_NAME("이미 존재하는 변수명입니다."),
    DEFINE_TYPE("이미 정의된 타입입니다."),
    VARIABLE_NAME_ERROR("사용할 수 없는 변수명 입니다."),
    VARIABLE_NAME_CLASS("클래스를 변수명으로 사용할 수 없습니다."),
    VARIABLE_NAME_NOT_USE("다음 키워드를 포함할 수 없습니다."),
    CREATE_KLASS_ERROR("클래스 생성에 실패하였습니다."),
    ACCESS_ERROR("해당 위치에 접근할 수 없습니다."),
    SYSTEM_KLASS_USE(KlassToken.SYSTEM.concat("클래스는 생성할 수 없습니다.")),
    STATIC_METHOD_NAME_ERROR(KlassToken.STATIC_METHOD.concat("는 클래스명과 일치할 수 없습니다."))
    ;

    private AtomicReference<String> errorCode;
    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case STATIC_METHOD_NAME_ERROR ->
                    KlassToken.STATIC_METHOD + " cannot match class name. %s" +
                    SeparatorToken.SEPARATOR_LINE +
                    "Please change name.";
            case SYSTEM_KLASS_USE ->
                    "You cannot create " + KlassToken.SYSTEM + " class. %s" +
                    SeparatorToken.SEPARATOR_LINE +
                    "Please correct the value.";
            case VALUE_ERROR ->
                    """
                    The variable value is invalid. %s
                    Please check the variable type.
                    """;
            case TYPE_ERROR ->
                    """
                    The variable type is invalid. %s
                    Please check the variable type.
                    """;
            case NO_DEFINE_NAME ->
                    """
                    Undefined variable name. %s
                    Please check the variable name.
                    """;
            case NO_DEFINE_TYPE ->
                    """
                    Undefined variable type. %s
                    Please check the variable type.
                    """;
            case ACCESS_ERROR ->
                    """
                    The location is inaccessible. %s
                    Please check your current location.
                    """;
            case DEFINE_NAME ->
                    """
                    Variable name that already exists. %s
                    Reserved words or already used variable names cannot be reused.
                    """;
            case DEFINE_TYPE ->
                    """
                    Variable type that already exists. %s
                    Reserved words or already used variable types cannot be reused.
                    """;
            case VARIABLE_NAME_ERROR ->
                    """
                    This is a variable name that cannot be used. %s
                    Please check the variable name.
                    """;
            case VARIABLE_NAME_CLASS ->
                    """
                    Classes cannot be used as variable names. %s
                    Please check the variable name.
                    """;
            case VARIABLE_NAME_NOT_USE ->
                    "Cannot contain the following keywords. %s" + SeparatorToken.SEPARATOR_LINE +
                    "keywords : (".concat(String.join(", ", NO_USE)).concat(")") + SeparatorToken.SEPARATOR_LINE +
                    "Please check the variable name.";
            case CREATE_KLASS_ERROR ->
                    """
                    Failed to create class. %s
                    Please check the definition status, parameters, name, etc.
                    """;
        };
    }

    @Override
    public Error getThrow(String errorCode) {
        if (this.errorCode == null) this.errorCode = new AtomicReference<>(errorCode);
        return new Error(this.name(), this.getClass());
    }
}
