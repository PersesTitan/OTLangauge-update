package bin.system.etc;

import bin.exception.MatchException;
import bin.token.KlassToken;
import work.StartWork;

public class Sleep extends StartWork {
    public Sleep() {
        super(KlassToken.SYSTEM, true, KlassToken.LONG_VARIABLE);
    }

    @Override
    protected void startItem(Object klassValue, Object[] params) {
        try {
            Thread.sleep((long) params[0]);
        } catch (InterruptedException e) {
            throw MatchException.SYSTEM_ERROR.getThrow(null);
        }
    }

    @Override
    public void reset() {}
}
