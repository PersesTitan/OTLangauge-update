package work;

import bin.Repository;
import bin.exception.VariableException;
import bin.token.CheckToken;
import lombok.Getter;

@Getter
public abstract class StartWork implements WorkTool {
    private final boolean isStatic;
    private final CreateWork<?> createWork;
    private final String[] params;
    private final int paramLen;

    public StartWork(String klassType, boolean isStatic, String... params) {
        this.params = params;
        this.isStatic = isStatic;
        this.paramLen = params.length;
        if (!CheckToken.isKlass(klassType)) throw VariableException.NO_DEFINE_TYPE.getThrow(klassType);
        this.createWork = Repository.createWorks.get(klassType);
        CheckToken.checkParamType(params);
        reset();
    }

    protected abstract void startItem(Object klassValue, Object[] params);

    public int start(Object klassValue, String params, int start) {
        Object startValues = this.getStartValues(this.createWork, this.isStatic, klassValue);
        Object[] startParams = this.getStartParams(this.createWork, this.paramLen, params, this.params);
        this.startItem(startValues, startParams);
        return start + 1;
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
