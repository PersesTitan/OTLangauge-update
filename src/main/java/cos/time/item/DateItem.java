package cos.time.item;

import bin.token.Token;
import cos.time.etc.TimeToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@RequiredArgsConstructor
public class DateItem {
    @Getter
    private final LocalDate date;
    public DateItem() {
        this.date = LocalDate.now();
    }

    public DateItem(int year, int month, int dayOfMonth) {
        this.date = LocalDate.of(year, month, dayOfMonth);
    }

    public DateItem(LocalDateTime dateTime) {
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int dayOfMonth = dateTime.getDayOfMonth();
        this.date = LocalDate.of(year, month, dayOfMonth);
    }

    public DateItem plus(ChronoUnit unit, long time) {
        return new DateItem(this.date.plus(time, unit));
    }

    public DateItem minus(ChronoUnit unit, long time) {
        return new DateItem(this.date.minus(time, unit));
    }

    public int get(ChronoField chronoField) {
        return this.date.get(chronoField);
    }

    @Override
    public String toString() {
        return TimeToken.DATE + Token.PARAM_S + this.date.getYear() + Token.PARAM_E +
                Token.PARAM_S + this.date.getMonth() + Token.PARAM_E +
                Token.PARAM_S + this.date.getDayOfMonth() + Token.PARAM_E;
    }
}
