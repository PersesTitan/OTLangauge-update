package cos.time.etc;

import lombok.Getter;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@Getter
public enum TimeItems {
    YEAR    (TimeToken.YEAR,    ChronoUnit.YEARS,   ChronoField.YEAR),
    MONTH   (TimeToken.MONTH,   ChronoUnit.MONTHS,  ChronoField.MONTH_OF_YEAR),
    WEEK    (TimeToken.WEEK,    ChronoUnit.WEEKS,   ChronoField.DAY_OF_WEEK),
    DAY     (TimeToken.DAY,     ChronoUnit.DAYS,    ChronoField.DAY_OF_MONTH),
    HOUR    (TimeToken.HOUR,    ChronoUnit.HOURS,   ChronoField.HOUR_OF_DAY),
    MINUTE  (TimeToken.MINUTE,  ChronoUnit.MINUTES, ChronoField.MINUTE_OF_HOUR),
    SECOND  (TimeToken.SECOND,  ChronoUnit.SECONDS, ChronoField.SECOND_OF_MINUTE),
    NANO    (TimeToken.NANO,    ChronoUnit.NANOS,   ChronoField.NANO_OF_SECOND);

    private final ChronoUnit unit;
    private final ChronoField field;
    private final String name;
    private final String plus;
    private final String minus;
    private final String get;

    TimeItems(String name, ChronoUnit unit, ChronoField field) {
        this.field = field;
        this.unit = unit;
        this.name = name;
        this.plus = "+".concat(name);
        this.minus = "-".concat(name);
        this.get = ">".concat(name);
    }
}