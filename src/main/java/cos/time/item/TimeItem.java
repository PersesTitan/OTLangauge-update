package cos.time.item;

import bin.token.Token;
import cos.time.etc.TimeToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public class TimeItem {
    @Getter
    private final LocalTime time;

    public TimeItem() {
        this.time = LocalTime.now();
    }

    public TimeItem(int hour, int minute, int second, int nano) {
        this.time = LocalTime.of(hour, minute, second, nano);
    }

    public TimeItem(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        int second = dateTime.getSecond();
        int nano = dateTime.getNano();
        this.time = LocalTime.of(hour, minute, second, nano);
    }

    public TimeItem plus(ChronoUnit unit, long time) {
        return new TimeItem(this.time.plus(time, unit));
    }

    public TimeItem minus(ChronoUnit unit, long time) {
        return new TimeItem(this.time.minus(time, unit));
    }

    public int get(ChronoField chronoField) {
        return this.time.get(chronoField);
    }

    @Override
    public String toString() {
        return TimeToken.TIME + Token.PARAM_S + this.time.getHour() + Token.PARAM_E +
                Token.PARAM_S + this.time.getMinute() + Token.PARAM_E +
                Token.PARAM_S + this.time.getSecond() + Token.PARAM_E +
                Token.PARAM_S + this.time.getNano() + Token.PARAM_E;
    }
}
