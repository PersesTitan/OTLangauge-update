package work;

import bin.apply.ReplaceType;
import bin.exception.SystemException;
import bin.exception.VariableException;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.variable.Types;
import lombok.Getter;

@Getter
public abstract class CreateWork<T> implements WorkTool {
    private final Class<T> klass;
    private final String klassName;
    private final String[] params;
    private final int paramLen;

    public CreateWork(Class<T> klass, String klassName, String...params) {
        this.klass = klass;
        this.klassName = klassName;
        this.params = params;
        this.paramLen = params.length;
        if (!(KlassToken.BASIC_KLASS.contains(klassName) && paramLen == 1)) {
            if (CheckToken.isKlass(klassName)) throw VariableException.DEFINE_TYPE.getThrow(klassName);
            CheckToken.checkParamType(params);
        }
        this.reset();
    }

    protected abstract Object createItem(Object[] params);

    public Object create(String params) {
        String[] param = EditToken.cutParams(this.paramLen, params);
        Object[] values = new Object[this.paramLen];
        for (int i = 0; i < this.paramLen; i++) {
            String type = this.params[i], value = param[i];
            // 타입이 문자열일때 그냥 값 넣기
            if (Types.STRING.originCheck(type)) values[i] = value;
            else values[i] = ReplaceType.replace(type, value);
        }
        return this.createItem(values);
    }

    public boolean check(Object value) {
        return value.getClass().equals(this.klass);
    }

    @Override
    public void reset() {}

    @Override
    public boolean isStatic() {
        throw SystemException.SYSTEM_ERROR.getThrow(null);
    }

    @Override
    public int getSize() {
        return this.paramLen;
    }
}
