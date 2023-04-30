package cos.macro.etc;

import bin.exception.Error;
import bin.exception.ExceptionTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@RequiredArgsConstructor
public enum MacroException implements ExceptionTool {
    DO_NOT_CREATE("클래스 생성에 실패하였습니다."),
    DO_NOT_CONTROL("컨트롤 조작에 실패하였습니다.")
    ;

    private AtomicReference<String> errorCode;
    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case DO_NOT_CONTROL ->
                    """
                    Control operation failed. %s
                    Please make sure that the environment is in which permissions and actions can be executed.
                    """;
            case DO_NOT_CREATE ->
                    """
                    Class creation failed. %s
                    Please check access rights and if there are any problems.
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
