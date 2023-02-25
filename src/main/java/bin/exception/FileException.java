package bin.exception;

import bin.token.SeparatorToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@RequiredArgsConstructor
public enum FileException implements ExceptionTool {
    DO_NOT_READ("파일을 읽을 수 없습니다."),
    DO_NOT_INCLUDE("클래스 추가에 실패하였습니다."),
    DO_NOT_PATH("해당 경로에 파일 및 디렉토리가 존재하지 않습니다."),
    FILE_TYPE_ERROR("파일 타입만 열 수 있습니다."),
    FILE_SAVE_NAME_ERROR("동일한 파일 이름이 존재합니다."),
    EXTENSION_MATCH_ERROR("해당 확장자 형식을 읽을 수 없습니다."),
    ;

    private AtomicReference<String> errorCode;
    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case DO_NOT_READ ->
                    """
                    File not found. %s
                    Please check the file location and path.
                    """;
            case DO_NOT_INCLUDE ->
                    """
                    Failed to add class. %s
                    Please download the file again or try again.
                    """;
            case DO_NOT_PATH ->
                    """
                    The file and directory do not exist in that path. %s
                    Please check the path.
                    """;
            case FILE_TYPE_ERROR ->
                    """
                    Only files can be opened. %s
                    Please check if the path is a file.
                    """;
            case EXTENSION_MATCH_ERROR ->
                    "The format of that extension could not be read. %s" + SeparatorToken.SEPARATOR_LINE +
                    "The extension currently readable by OTLanguage is ( " + Arrays.toString(SeparatorToken.extensions) + ")";
            case FILE_SAVE_NAME_ERROR ->
                    """
                    The same file name exists. %s
                    Please change the file name.
                    """;
        };
    }

    public Error getThrow(String errorCode) {
        if (this.errorCode == null) this.errorCode = new AtomicReference<>(errorCode);
        return new Error(this.name(), this.getClass());
    }
}
