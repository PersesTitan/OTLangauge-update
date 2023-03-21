package bin.variable.custom;

import bin.exception.VariableException;
import bin.variable.Types;

import java.util.stream.Stream;

public interface CustomTool {
    default <T> Object sum(Types types, Stream<T> stream, String klassType) {
        return switch (types) {
            case CHARACTER, INTEGER -> stream.mapToInt(v -> (int) v).sum();
            case LONG -> stream.mapToLong(v -> (long) v).sum();
            case FLOAT -> (float) stream.mapToDouble(v -> (float) v).sum();
            case DOUBLE -> stream.mapToDouble(v -> (double) v).sum();
            case BOOLEAN, STRING -> throw VariableException.TYPE_ERROR.getThrow(klassType);
        };
    }

    default <T> Object max(Types types, Stream<T> stream, String klassType) {
        return switch (types) {
            case CHARACTER, INTEGER -> stream.mapToInt(v -> (int) v).max().orElse(0);
            case LONG -> stream.mapToLong(v -> (long) v).max().orElse(0L);
            case FLOAT -> (float) stream.mapToDouble(v -> (float) v).max().orElse(0f);
            case DOUBLE -> stream.mapToDouble(v -> (double) v).max().orElse(0d);
            case BOOLEAN, STRING -> throw VariableException.TYPE_ERROR.getThrow(klassType);
        };
    }

    default <T> Object min(Types types, Stream<T> stream, String klassType) {
        return switch (types) {
            case CHARACTER, INTEGER -> stream.mapToInt(v -> (int) v).min().orElse(0);
            case LONG -> stream.mapToLong(v -> (long) v).min().orElse(0L);
            case FLOAT -> (float) stream.mapToDouble(v -> (float) v).min().orElse(0f);
            case DOUBLE -> stream.mapToDouble(v -> (double) v).min().orElse(0d);
            case BOOLEAN, STRING -> throw VariableException.TYPE_ERROR.getThrow(klassType);
        };
    }
}
