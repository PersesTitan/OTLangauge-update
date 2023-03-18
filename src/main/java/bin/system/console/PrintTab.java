package bin.system.console;

import bin.token.KlassToken;
import bin.variable.Types;
import work.StartWork;

public class PrintTab extends StartWork {
    public PrintTab() {
        super(KlassToken.SYSTEM, true, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected void startItem(Object klassValue, Object[] params) {
        System.out.print(Types.toString(params[0]).concat("\t"));
    }

    @Override
    public void reset() {}
}
