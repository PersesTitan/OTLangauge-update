package cos.color.item;

import bin.exception.Error;
import bin.exception.ExceptionTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@RequiredArgsConstructor
public enum ColorException implements ExceptionTool {
    COLOR_VALUE_ERROR("유효하지 않는 값입니다.")
    ;

    private AtomicReference<String> errorCode;
    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case COLOR_VALUE_ERROR ->
                    """
                    Invalid value. %s
                    Values range from 0 to 255.
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
