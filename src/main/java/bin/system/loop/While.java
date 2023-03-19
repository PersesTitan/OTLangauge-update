package bin.system.loop;

import bin.apply.Read;
import bin.apply.mode.LoopMode;
import bin.repository.code.CodeMap;
import bin.token.KlassToken;
import work.LoopWork;

public class While extends LoopWork {
    public While() {
        super(LoopMode.NONE, KlassToken.SYSTEM, true, KlassToken.BOOL_VARIABLE);
    }

    @Override
    protected int loopItem(int start, int end,
                           LoopMode mode, CodeMap code, String repoKlass,
                           Object klassValue, Object[] params) {
        if ((boolean) params[0]) {
            Read.read(code, start, end, null);
            return start-1;
        } else return end;
    }

    @Override
    public void reset() {}
}
