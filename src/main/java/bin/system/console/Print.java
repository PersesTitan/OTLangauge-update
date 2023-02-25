package bin.system.console;

import bin.apply.item.KlassItem;
import bin.token.KlassToken;
import bin.variable.Types;
import work.StartWork;

public class Print extends StartWork {
    public Print() {
        super(KlassToken.SYSTEM, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected void startItem(Object value, Object[] params) {
        System.out.print(Types.toString(params[0]));
    }

    @Override
    public void reset() {}
}
