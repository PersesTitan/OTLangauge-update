package work;

import bin.Repository;
import bin.apply.Replace;
import bin.exception.VariableException;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;
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
        if (KlassToken.BASIC_KLASS.contains(klassName) && paramLen == 1) return;
        if (Repository.isKlass(klassName)) throw VariableException.DEFINE_TYPE.getThrow(klassName);
        Repository.checkParamType(params);
        this.reset();
    }

    protected abstract Object createItem(Object[] params);

    public Object create(String params) {
        if (KlassToken.BASIC_KLASS.contains(this.klassName)) return this.createItem(new Object[]{params});
        String[] param = EditToken.cutParams(this.paramLen, params);
        Object[] values = new Object[this.paramLen];
        for (int i = 0; i < this.paramLen; i++) {
            String type = this.params[i], value = param[i];
            // 타입이 문자열일때 그냥 값 넣기
            if (Types.STRING.originCheck(type)) values[i] = value;
            else {
                // 변수명인지 확인하는 로직
                if (Repository.repositoryArray.find(type, value)) {
                    values[i] = Repository.repositoryArray.get(type, value);
                } else {
                    CreateWork<?> createWork = Repository.createWorks.get(type);
                    if (createWork.paramLen > 1) {
                        values[i] = EditToken.startWith(value, Token.PARAM_S) && EditToken.endWith(value, Token.PARAM_E)
                                ? createWork.create(value)
                                : this.klass.cast(Replace.replace(value));
                    } else {
                        values[i] = value.contains(Token.ACCESS)
                                ? this.klass.cast(Replace.replace(value))
                                : createWork.create(value);
                    }
                }
            }
        }
        return this.createItem(values);
    }

    public boolean check(Object value) {
        return value.getClass().equals(this.klass);
    }
}
