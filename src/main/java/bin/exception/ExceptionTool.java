package bin.exception;

import jdk.dynalink.Operation;

import java.util.concurrent.atomic.AtomicReference;

public interface ExceptionTool {
    String getSubMessage();                 // 지속적으로 추가
    String getMessage();                    // 자동 생성
    AtomicReference<String> getErrorCode(); // 자동 생성
    Error getThrow(String errorCode);

    default <T extends Enum<T> & Operation & ExceptionTool> T get(Class<T> klass, String message) {
        return T.valueOf(klass, message);
    }
}
