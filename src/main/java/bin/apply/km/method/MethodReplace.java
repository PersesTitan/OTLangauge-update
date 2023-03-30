package bin.apply.km.method;

import bin.apply.km.klass.KlassItem;
import bin.apply.loop.Loop;
import bin.exception.MatchException;
import bin.exception.SystemException;
import work.ReplaceWork;

public class MethodReplace extends ReplaceWork {
    private final DefineMethod method;

    public MethodReplace(DefineMethod method, boolean isStatic) {
        super(method.getKlassName(), method.getReturnType(), isStatic, method.getParamType());
        if (method.getReturnVarName() == null) {
            String errorMessage = method.getCode().get(method.getEnd());
            throw SystemException.SYSTEM_ERROR.getThrow(errorMessage);
        }
        this.method = method;
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        return Loop.startMethod(this.method, (KlassItem) klassValue, params);
    }
}
