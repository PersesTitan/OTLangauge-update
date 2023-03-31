package bin.apply.km.method;

import bin.apply.km.klass.KlassItem;
import bin.apply.loop.Loop;
import bin.exception.MatchException;
import bin.exception.SystemException;
import work.StartWork;

public class MethodVoid extends StartWork {
    private final DefineMethod method;

    public MethodVoid(DefineMethod method, boolean isStatic) {
        super(method.getKlassName(), isStatic, method.getParamType());
        if (method.getReturnVarName() != null) {
            String errorMessage = method.getCode().get(method.getEnd());
            throw SystemException.SYSTEM_ERROR.getThrow(errorMessage);
        }
        this.method = method;
    }

    @Override
    protected void startItem(Object klassValue, Object[] params) {
        Loop.startMethod(this.method, (KlassItem) klassValue, params);
    }
}
