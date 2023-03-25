package bin.system.console.out;

import bin.token.KlassToken;
import bin.variable.Types;
import work.StartWork;

public class Print extends StartWork {
    public Print() {
        super(KlassToken.SYSTEM, true, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected void startItem(Object klassValue, Object[] params) {
        System.out.print(Types.toString(params[0]));
    }

    @Override
    public void reset() {}
}
