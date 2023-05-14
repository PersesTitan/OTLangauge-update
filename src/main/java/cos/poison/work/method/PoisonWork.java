package cos.poison.work.method;

import bin.Repository;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.repository.code.CodeMap;
import bin.token.EditToken;
import cos.poison.etc.PoisonToken;
import work.LoopWork;

public abstract class PoisonWork extends LoopWork {
    public PoisonWork() {
        super(LoopMode.PUT, PoisonToken.POISON, false);
    }

    @Override
    protected abstract int loopItem(int start, int end, LoopMode mode, CodeMap code,
                                    String repoKlass, Object klassValue, Object[] params);

    @Override
    public int loop(Object klassValue, String params, String path, int start, String repoKlass) {
        CodeMap code = Repository.codes.get(path);

        int end = LoopMode.next(path, start);
        LoopMode mode = LoopMode.getMode(code.get(end));
        if (!LoopMode.PUT.equals(mode)) throw MatchException.GRAMMAR_ERROR.getThrow(code.get(end));

        Object startValues = this.getStartValues(this.getCreateWork(), false, klassValue);
        Object[] startParams = EditToken.cutParams(-1, params);
        return this.loopItem(start, end, mode, code, repoKlass, startValues, startParams) + 1;
    }
}
