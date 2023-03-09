package bin.apply.km.method;

import bin.apply.km.klass.KlassItem;
import bin.apply.loop.Loop;
import bin.exception.MatchException;
import bin.repository.TypeMap;
import work.StartWork;

public class MethodVoid extends StartWork {
    private final DefineMethod method;

    public MethodVoid(DefineMethod method) {
        super(method.getKlassName(), false, method.getParamType());
        if (method.getReturnVarName() != null) {
            String errorMessage = method.getCode().get(method.getEnd());
            throw MatchException.SYSTEM_ERROR.getThrow(errorMessage);
        }
        this.method = method;
    }

    @Override
    protected void startItem(Object klassValue, Object[] params) {
        KlassItem klassItem = (KlassItem) klassValue;
        TypeMap methodRepository = new TypeMap();
        Loop.SET_KM(klassItem.getRepository(), methodRepository, () -> {
            // 초기 파라미터 값 넣기
            this.method.setParam(methodRepository, params);
            this.method.start();
        });
    }

    @Override
    public void reset() {}
}
