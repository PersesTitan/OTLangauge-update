package cos.poison.work;

import bin.token.KlassToken;
import cos.poison.etc.PoisonToken;
import cos.poison.item.HttpItem;
import cos.poison.item.PoisonItem;
import work.StartWork;

import java.util.Calendar;
import java.util.Date;

public class PoisonSetCookie extends StartWork {
    public PoisonSetCookie() {
        super(
                PoisonToken.POISON, false,
                KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE,
                KlassToken.STRING_VARIABLE, KlassToken.INT_VARIABLE
        );
    }

    @Override
    protected void startItem(Object klassValue, Object[] params) {
        HttpItem item = ((PoisonItem) klassValue).getHttpItem();
        String key = params[0].toString();
        String value = params[1].toString();
        String path = params[2].toString();
        int maxAge = (int) params[3];

        StringBuilder cookie = new StringBuilder();
        cookie.append(key).append('=').append(value);
        if (maxAge > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.SECOND, maxAge);
            cookie.append("; expires=").append(calendar.getTime());
            cookie.append("; Max-Age=").append(maxAge);
        }
        cookie.append("; path=").append((path == null || path.isEmpty()) ? '/' : path);
        item.exchange().getResponseHeaders().add("Set-Cookie", cookie.toString());
    }
}
