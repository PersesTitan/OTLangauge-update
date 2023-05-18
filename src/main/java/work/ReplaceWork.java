package work;

import bin.Repository;
import bin.exception.VariableException;
import bin.token.CheckToken;
import lombok.Getter;

@Getter
public abstract class ReplaceWork implements WorkTool {
    private final boolean isStatic;
    private final CreateWork<?> returnType;
    private final CreateWork<?> createWork;
    private final String[] params;
    private final int paramLen;

    public ReplaceWork(String klassType, String returnType, boolean isStatic, String... params) {
        this.params = params;
        this.isStatic = isStatic;
        this.paramLen = params.length;
        if (!CheckToken.isKlass(returnType)) throw VariableException.NO_DEFINE_TYPE.getThrow(returnType);
        if (!CheckToken.isKlass(klassType)) throw VariableException.NO_DEFINE_TYPE.getThrow(klassType);
        this.returnType = Repository.createWorks.get(returnType);
        this.createWork = Repository.createWorks.get(klassType);
        CheckToken.checkParamType(params);
    }

    protected abstract Object replaceItem(Object klassValue, Object[] params);

    public Object replace(Object klassValue, String params) {
        Object startValues = this.getStartValues(this.createWork, this.isStatic, klassValue);
        Object[] startParams = this.getStartParams(this.createWork, this.paramLen, params, this.params);

        Object value = this.replaceItem(startValues, startParams);
        if (this.returnType.check(value)) return value;
        else throw VariableException.VALUE_ERROR.getThrow("return type error : " + this.returnType.getKlass());
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
