package bin.system.etc;

import bin.token.KlassToken;
import work.StartWork;

public class Quit extends StartWork {
    public Quit() {
        super(KlassToken.SYSTEM, true);
    }

    @Override
    protected void startItem(Object klassValue, Object[] params) {
        System.exit(0);
    }

    @Override
    public void reset() {}
}
