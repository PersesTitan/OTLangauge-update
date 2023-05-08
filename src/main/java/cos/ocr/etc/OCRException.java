package cos.ocr.etc;

import bin.exception.Error;
import bin.exception.ExceptionTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@RequiredArgsConstructor
public enum OCRException implements ExceptionTool {
    DO_NOT_FIND_LANGUAGE("존재하지 않는 언어 데이터 입니다."),
    NOT_HAVE_LANGUAGE("설정된 언어가 존재하지 않습니다."),
    NOT_INTI("언어 초기화가 되지 않았습니다. " + OCRToken.INIT_LANGUAGE)
    ;

    private AtomicReference<String> errorCode;
    private final String message;


    @Override
    public String getSubMessage() {
        return switch (this) {
            case NOT_INTI ->
                    """
                    Language initialization failed. %d
                    Please reset the language.
                    """;
            case NOT_HAVE_LANGUAGE ->
                    """
                    The language set does not exist. %d
                    Please add a language.
                    """;
            case DO_NOT_FIND_LANGUAGE ->
                    """
                    Language data does not exist. %s
                    Please check the language name.
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
