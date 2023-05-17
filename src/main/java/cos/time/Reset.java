package cos.time;

import bin.Repository;
import cos.time.create.CreateDate;
import cos.time.create.CreateDateTime;
import cos.time.create.CreateTime;
import cos.time.etc.TimeItems;
import cos.time.etc.TimeToken;
import cos.time.item.DateItem;
import cos.time.item.DateTimeItem;
import cos.time.item.TimeItem;
import work.ResetWork;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class Reset implements ResetWork, TimeToken {
    @Override
    public void reset() {
        Repository.createWorks.put(TIME, new CreateTime());
        Repository.createWorks.put(DATE, new CreateDate());
        Repository.createWorks.put(DATE_TIME, new CreateDateTime());

        AddWork<TimeItem> timeWork = new AddWork<>(TIME);
        AddWork<DateItem> dateWork = new AddWork<>(DATE);
        AddWork<DateTimeItem> dateTimeWork = new AddWork<>(DATE_TIME);

        timeWork.addSR(TIME, NOW, TimeItem::new);
        dateWork.addSR(DATE, NOW, DateItem::new);
        dateTimeWork.addSR(DATE_TIME, NOW, DateTimeItem::new);

        Set.of(TimeItems.NANO, TimeItems.SECOND, TimeItems.MINUTE, TimeItems.HOUR).forEach(v -> {
            ChronoUnit unit = v.getUnit();
            ChronoField field = v.getField();
            timeWork.addR(TIME, v.getPlus(), l, (t, o) -> t.plus(unit, (long) o));
            timeWork.addR(TIME, v.getMinus(), l, (t, o) -> t.minus(unit, (long) o));
            dateWork.addR(DATE_TIME, v.getPlus(), l, (t, o) -> t.plus(unit, (long) o));
            dateWork.addR(DATE_TIME, v.getMinus(), l, (t, o) -> t.minus(unit, (long) o));
            dateTimeWork.addR(i, v.getGet(), t -> t.get(field));
            timeWork.addR(i, v.getGet(), t -> t.get(field));

//            Repository.replaceWorks.put(TIME, TIME, v.getPlus(), new TimePlus(v.getUnit()));
//            Repository.replaceWorks.put(TIME, TIME, v.getMinus(), new TimeMinus(v.getUnit()));
//            Repository.replaceWorks.put(DATE_TIME, DATE_TIME, v.getPlus(), new DateTimePlus(v.getUnit()));
//            Repository.replaceWorks.put(DATE_TIME, DATE_TIME, v.getMinus(), new DateTimeMinus(v.getUnit()));
//            Repository.replaceWorks.put(KlassToken.INT_VARIABLE, DATE_TIME, v.getGet(), new DateTimeGet(v.getField()));
//            Repository.replaceWorks.put(KlassToken.INT_VARIABLE, TIME, v.getGet(), new TimeGet(v.getField()));
        });
        Set.of(TimeItems.DAY, TimeItems.WEEK, TimeItems.MONTH, TimeItems.YEAR).forEach(v -> {
            ChronoUnit unit = v.getUnit();
            ChronoField field = v.getField();
            dateWork.addR(DATE, v.getPlus(), l, (t, o) -> t.plus(unit, (long) o));
            dateWork.addR(DATE, v.getMinus(), l, (t, o) -> t.minus(unit, (long) o));
            dateTimeWork.addR(DATE_TIME, v.getPlus(), l, (t, o) -> t.plus(unit, (long) o));
            dateTimeWork.addR(DATE_TIME, v.getMinus(), l, (t, o) -> t.minus(unit, (long) o));
            dateWork.addR(i, v.getGet(), t -> t.get(field));
            dateTimeWork.addR(i, v.getGet(), t -> t.get(field));

//            Repository.replaceWorks.put(DATE, DATE, v.getPlus(), new DatePlus(v.getUnit()));
//            Repository.replaceWorks.put(DATE, DATE, v.getMinus(), new DateMinus(v.getUnit()));
//            Repository.replaceWorks.put(DATE_TIME, DATE_TIME, v.getPlus(), new DateTimePlus(v.getUnit()));
//            Repository.replaceWorks.put(DATE_TIME, DATE_TIME, v.getMinus(), new DateTimeMinus(v.getUnit()));
//            Repository.replaceWorks.put(KlassToken.INT_VARIABLE, DATE_TIME, v.getGet(), new DateTimeGet(v.getField()));
//            Repository.replaceWorks.put(KlassToken.INT_VARIABLE, DATE, v.getGet(), new DateGet(v.getField()));
        });
    }
}
