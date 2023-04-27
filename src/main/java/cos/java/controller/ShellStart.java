package cos.java.controller;

import bin.apply.mode.LoopMode;
import bin.repository.code.CodeMap;
import work.LoopWork;

public class ShellStart extends LoopWork {
    public ShellStart(LoopMode[] modes, String klassType, boolean isStatic, String... params) {
        super(modes, klassType, isStatic, params);
    }

    @Override
    protected int loopItem(int start, int end, LoopMode mode, CodeMap code,
                           String repoKlass, Object klassValue, Object[] params) {

        return end;
    }

    @Override
    public void reset() {}
}
