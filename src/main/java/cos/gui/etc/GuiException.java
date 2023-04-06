package cos.gui.etc;

import bin.exception.Error;
import bin.exception.ExceptionTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@RequiredArgsConstructor
public enum GuiException implements ExceptionTool {
    DO_NOT_SUPPORT_TYPE("지원하지 않는 종류입니다."),
    DO_NOT_USE_TYPE("해당 타입에서는 지원하지 않습니다.")
    ;
    private AtomicReference<String> errorCode;
    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case DO_NOT_SUPPORT_TYPE ->
                    """
                    This type is not supported. %s
                    Please check this name.
                    """;
            case DO_NOT_USE_TYPE ->
                    """
                    This type does not support it. %s
                    Please check the item type.
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
