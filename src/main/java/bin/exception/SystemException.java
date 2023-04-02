package bin.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@RequiredArgsConstructor
public enum SystemException implements ExceptionTool {
    SYSTEM_ERROR("시스템 에러가 발생하였습니다."),
    RIGHT_ERROR("접근권한이 없거나 사용할 수 없습니다."),
    IMPORT_ERROR("해당 기능을 사용하기 위해서 필요한 모듈을 찾을 수 없습니다."),
    CREATE_ERROR("객체 생성에 실패하였습니다.")
    ;

    private AtomicReference<String> errorCode;
    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case CREATE_ERROR ->
                    """
                    Object creation failed. %s
                    """;
            case IMPORT_ERROR ->
                    """
                    The module required to use the feature could not be found. %s
                    Please install the necessary modules.
                    """;
            case SYSTEM_ERROR ->
                    """
                    A system error has occurred. %s
                    A problem has occurred that should not have occurred.
                    Please contact the developer.
                    """;
            case RIGHT_ERROR ->
                    """
                    You do not have access or are not available. %s
                    Please check if you are already using it or if you have permission.
                    """;
        };
    }

    @Override
    public Error getThrow(String errorCode) {
        if (this.errorCode == null) this.errorCode = new AtomicReference<>(errorCode);
        return new Error(this.name(), this.getClass());
    }
}
