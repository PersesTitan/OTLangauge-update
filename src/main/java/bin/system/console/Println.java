package bin.system.console;

import bin.token.KlassToken;
import bin.variable.Types;
import work.StartWork;

public class Println extends StartWork {
    public Println() {
        super(KlassToken.SYSTEM, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected void startItem(Object value, Object[] params) {
        System.out.println(Types.toString(params[0]));
    }

    @Override
    public void reset() {

    }
}
