package work;

import bin.Repository;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.EditToken;
import bin.variable.Types;

public abstract class StartWork implements WorkTool {
    private final CreateWork<?> createWork;
    private final String[] params;
    private final int paramLen;

    public StartWork(String klassType, String... params) {
        this.params = params;
        this.paramLen = params.length;
        if (!Repository.isKlass(klassType)) throw VariableException.NO_DEFINE_TYPE.getThrow(klassType);
        this.createWork = Repository.createWorks.get(klassType);
        Repository.checkParamType(params);
    }

    protected abstract void startItem(Object value, Object[] params);

    public void start(Object klassValue, String params) {
        switch (this.paramLen) {
            case 0:
                if (params != null) throw MatchException.PARAM_COUNT_ERROR.getThrow(params);
                if (klassValue != null) throw MatchException.GRAMMAR_ERROR.getThrow(klassValue.toString());
                this.startItem(null, null);
                break;
            case 1:
                if (params == null) {
                    if (Types.STRING.originCheck(this.params[0])) {
                        this.startItem(klassValue, new Object[] {""});
                        break;
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
                this.startItem(klassValue, values);
                break;
        }
    }
}
