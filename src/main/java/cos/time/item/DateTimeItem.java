package cos.time.item;

import bin.token.Token;
import cos.time.etc.TimeToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@Getter
@RequiredArgsConstructor
public class DateTimeItem {
    private final LocalDateTime dateTime;

    public DateTimeItem() {
        this.dateTime = LocalDateTime.now();
    }

    public DateTimeItem(DateItem date, TimeItem time) {
        this.dateTime = LocalDateTime.of(date.getDate(), time.getTime());
    }

    public DateTimeItem plus(ChronoUnit unit, long time) {
        return new DateTimeItem(this.dateTime.plus(time, unit));
    }

    public DateTimeItem minus(ChronoUnit unit, long time) {
        return new DateTimeItem(this.dateTime.minus(time, unit));
    }

    public int get(ChronoField chronoField) {
        return this.dateTime.get(chronoField);
    }

    @Override
    public String toString() {
        return TimeToken.DATE_TIME + Token.PARAM_S + new TimeItem(dateTime) + Token.PARAM_E +
                Token.PARAM_S + new DateItem(dateTime) + Token.PARAM_E;
    }
}
