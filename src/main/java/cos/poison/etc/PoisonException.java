package cos.poison.etc;

import bin.exception.Error;
import bin.exception.ExceptionTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@RequiredArgsConstructor
public enum PoisonException implements ExceptionTool {
    CREATE_SERVER_ERROR("서버 생성에 실패하였습니다."),
    NO_CREATE_SERVER("서버가 생성되어 있지 않습니다."),
    DO_NOT_READ("데이터를 읽는데 실패하였습니다."),
    DATA_PATTERN_ERROR("데이터 값이 유효하지 않습니다."),
    HAVE_PATH_ERROR("이미 정의된 경로입니다."),
    DATA_TYPE_ERROR("데이터 타입이 일치하지 않습니다."),
    METHOD_TYPE_ERROR("지원하지 않는 메소드 타입입니다."),
    URL_REGEXP_ERROR("URL 변수값이 유효하지 않습니다."),
    URL_SYNTAX_ERROR("중복된 변수값이 존재합니다."),
    URL_FIND_ERROR("URL 안에 일치하는 이름이 존재하지 않습니다."),
    DO_NOT_RUN("해당 위치에서 실행할 수 없습니다."),
    IN_DATA_TYPE_ERROR("지원하지 않는 데이터 타입입니다.")
    ;

    private AtomicReference<String> errorCode;
    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case IN_DATA_TYPE_ERROR ->
                    """
                    Unsupported data type. %d
                    Please check the value.
                    """;
            case DO_NOT_RUN ->
                    """
                    Unable to run from that location. %s
                    Please check the grammar.
                    """;
            case URL_FIND_ERROR ->
                    """
                    No matching name exists in the URL. %s
                    Please add the value.
                    """;
            case URL_SYNTAX_ERROR ->
                    """
                    Duplicate variable value exists. %s
                    Please change the value.
                    """;
            case URL_REGEXP_ERROR ->
                    """
                    The url variable value is invalid. %s
                    Please change the name.
                    """;
            case METHOD_TYPE_ERROR ->
                    """
                    This method type is not supported. %s
                    Please change the method type.
                    """;
            case DATA_TYPE_ERROR ->
                    """
                    Data types do not match. %s
                    Please check the data type.
                    """;
            case HAVE_PATH_ERROR ->
                    """
                    Path already defined. %s
                    Please change the path value.
                    """;
            case DATA_PATTERN_ERROR ->
                    """
                    The data value is invalid. %s
                    Please check the data.
                    """;
            case CREATE_SERVER_ERROR ->
                    """
                    Server creation failed. %s
                    Please check the server permission and status.
                    """;
            case NO_CREATE_SERVER ->
                    """
                    No server has been created. %s
                    Please create a server.
                    """;
            case DO_NOT_READ ->
                    """
                    Failed to read data. %s
                    Please try again.
                    """;
        };
    }

    @Override
    public Error getThrow(String errorCode) {
        if (this.errorCode == null) this.errorCode = new AtomicReference<>(errorCode);
        else this.errorCode.set(errorCode);
        return new Error(this.name(), this.getClass());
    }
}
