package bin.system.console.out;

import bin.token.KlassToken;
import bin.variable.Types;
import work.StartWork;

public class PrintSpace extends StartWork {
    public PrintSpace() {
        super(KlassToken.SYSTEM, true, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected void startItem(Object klassValue, Object[] params) {
        System.out.print(Types.toString(params[0]).concat(" "));
    }

    @Override
    public void reset() {}
}
