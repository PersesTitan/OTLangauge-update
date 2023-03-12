package cos.math.etc;

import bin.exception.Error;
import bin.exception.ExceptionTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@RequiredArgsConstructor
public enum MathException implements ExceptionTool {
    RANDOM_BOUND_ERROR("값이 0이하 일 수 없습니다.")
    ;

    private AtomicReference<String> errorCode;
    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case RANDOM_BOUND_ERROR ->
                    """
                    Value cannot be less than zero. %s
                    Please enter a value greater than or equal to zero.
                    """;
        };
    }

    @Override
    public Error getThrow(String errorCode) {
        if (this.errorCode == null) this.errorCode = new AtomicReference<>(errorCode);
        return new Error(this.name(), this.getClass());
    }
}
