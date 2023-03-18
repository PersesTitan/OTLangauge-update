package bin.system.except;

import bin.apply.Read;
import bin.apply.mode.LoopMode;
import bin.repository.code.CodeMap;
import bin.token.KlassToken;
import work.LoopWork;

public class Try extends LoopWork {
    public Try() {
        super(LoopMode.NONE, KlassToken.SYSTEM, true);
    }

    @Override
    protected int loopItem(int start, int end,
                           LoopMode mode, CodeMap code, String repoKlass,
                           Object klassValue, Object[] params) {
        try {
            Read.read(code, start, end, repoKlass);
        } catch (Exception ignored) {}
        return end;
    }

    @Override
    public void reset() {}
}
