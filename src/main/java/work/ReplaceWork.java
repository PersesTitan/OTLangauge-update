package work;

import bin.Repository;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.EditToken;
import bin.variable.Types;

public abstract class ReplaceWork implements WorkTool {
    private final CreateWork<?> createWork;
    private final String[] params;
    private final int paramLen;

    public ReplaceWork(String klassType, String... params) {
        this.params = params;
        this.paramLen = params.length;
        if (!Repository.isKlass(klassType)) throw VariableException.NO_DEFINE_TYPE.getThrow(klassType);
        this.createWork = Repository.createWorks.get(klassType);
        Repository.checkParamType(params);
    }

    protected abstract Object replaceItem(Object klassValue, Object[] params);

    public Object replace(Object klassValue, String params) {
        this.checkType(this.createWork, klassValue);
        switch (this.paramLen) {
            case 0:
                if (params != null) throw MatchException.PARAM_COUNT_ERROR.getThrow(params);
                if (klassValue != null) throw MatchException.GRAMMAR_ERROR.getThrow(klassValue.toString());
                return this.replaceItem(null, null);
            case 1:
                if (params == null) {
                    if (Types.STRING.originCheck(this.params[0])) {
                        return this.replaceItem(klassValue, new Object[] {""});
                    } else {
                        String errorMessage = createWork.getKlassName()
                                .concat(" ")
                                .concat(String.join(", ", this.params));
                        throw MatchException.PARAM_COUNT_ERROR.getThrow(errorMessage);
                    }
                }
            default:
                String[] param = EditToken.cutParams(this.paramLen, params);
                Object[] values = new Object[this.paramLen];
                for (int i = 0; i < this.paramLen; i++)
                    values[i] = Repository.createWorks.get(this.params[i]).create(param[i]);
                return this.replaceItem(klassValue, values);
        }
    }
}
