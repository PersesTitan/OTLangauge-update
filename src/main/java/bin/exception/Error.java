package bin.exception;

import bin.Setting;
import bin.apply.mode.RunMode;
import bin.token.SeparatorToken;
import jdk.dynalink.Operation;

public class Error extends RuntimeException {
    private final Object klassInfo;

    public Error(String message, Object klassInfo) {
        super(message);
        this.klassInfo = klassInfo;
    }

    private <T extends Enum<T> & Operation & ExceptionTool> StringBuilder createMessage(T t) {
        StringBuilder message = new StringBuilder();
        message.append("OTLanguage.").append(t.getDeclaringClass().getSimpleName()).append(": ").append(t.getMessage());
        return message;
    }

    private <T extends Enum<T> & Operation & ExceptionTool> String makeSubMessage(T t) {
        final String patternText = "(^|" + SeparatorToken.SEPARATOR_LINE + ")";
        final String replaceText = SeparatorToken.SEPARATOR_LINE.concat("\totl ");
        String errorCode = t.getErrorCode().getAndSet(null);
        return String.format(t.getSubMessage().strip().replaceAll(patternText, replaceText),
                errorCode == null ? "" : "(".concat(errorCode).concat(")"));
    }

    public <T extends Enum<T> & Operation & ExceptionTool> void print() {
        T t = T.valueOf((Class<T>) klassInfo, super.getMessage());
        StringBuilder message = createMessage(t);
        message.append(makeSubMessage(t));

        if (RunMode.getNormal()) System.err.println(message);
        else Setting.errorMessage(message.toString());
    }

    public <T extends Enum<T> & Operation & ExceptionTool> void print(String path) {
        T t = T.valueOf((Class<T>) klassInfo, super.getMessage());
        StringBuilder message = createMessage(t);
        message.append(SeparatorToken.SEPARATOR_LINE).append("\totl Path (").append(path).append(')');
        message.append(makeSubMessage(t));

        if (RunMode.getNormal()) System.err.println(message);
        else Setting.errorMessage(message.toString());
    }

    public <T extends Enum<T> & Operation & ExceptionTool> void print(String path, String line, int pos) {
        T t = T.valueOf((Class<T>) klassInfo, super.getMessage());
        StringBuilder message = createMessage(t);
        message.append(SeparatorToken.SEPARATOR_LINE).append("\totl file location where it occurred(").append(path).append(':').append(pos).append(')');
        message.append(SeparatorToken.SEPARATOR_LINE).append("\totl line that occurred(").append(line).append(")");
        message.append(makeSubMessage(t));

        if (RunMode.getNormal()) System.err.println(message);
        else Setting.errorMessage(message.toString());
    }
}
