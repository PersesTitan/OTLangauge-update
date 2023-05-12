package work;

import bin.Repository;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.exception.SystemException;
import bin.exception.VariableException;
import bin.repository.code.CodeMap;
import bin.token.CheckToken;
import lombok.Getter;

public abstract class LoopWork implements WorkTool {
    @Getter
    private final boolean isStatic;
    @Getter
    private final CreateWork<?> createWork;
    private final String[] params;
    private final int paramLen;
    private final LoopMode[] modes;

    public LoopWork(LoopMode[] modes, String klassType, boolean isStatic, String... params) {
        this.modes = modes;
        this.params = params;
        this.isStatic = isStatic;
        this.paramLen = params.length;
        for (LoopMode mode : modes) {
            switch (mode) {
                case OTHER, RETURN -> throw SystemException.SYSTEM_ERROR.getThrow(mode);
            }
        }
        if (!CheckToken.isKlass(klassType)) throw VariableException.NO_DEFINE_TYPE.getThrow(klassType);
        this.createWork = Repository.createWorks.get(klassType);
        CheckToken.checkParamType(params);
    }

    public LoopWork(LoopMode modes, String klassType, boolean isStatic, String... params) {
        this(new LoopMode[] {modes}, klassType, isStatic, params);
    }

    protected abstract int loopItem(int start, int end, LoopMode mode, CodeMap code, String repoKlass,
                                    Object klassValue, Object[] params);

    public int loop(Object klassValue, String params, String path, int start, String repoKlass) {
        CodeMap code = Repository.codes.get(path);

        int end = LoopMode.next(path, start);
        LoopMode mode = LoopMode.getMode(code.get(end));
        if (!this.isMode(mode)) throw MatchException.GRAMMAR_ERROR.getThrow(code.get(end));

        Object startValues = this.getStartValues(this.createWork, this.isStatic, klassValue);
        Object[] startParams = this.getStartParams(this.createWork, this.paramLen, params, this.params);
        return this.loopItem(start, end, mode, code, repoKlass, startValues, startParams) + 1;
    }

    private boolean isMode(LoopMode loopMode) {
        if (this.modes.length == 1) return this.modes[0].equals(loopMode);
        for (LoopMode mode : this.modes) {
            if (mode.equals(loopMode)) return true;
        }
        return false;
    }

    @Override
    public void reset() {}

    @Override
    public int getSize() {
        return this.paramLen;
    }

    @Override
    public String toString() {
        return getString(this.createWork, this.paramLen, this.params);
    }
}
