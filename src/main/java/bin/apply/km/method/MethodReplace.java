package bin.apply.km.method;

import bin.Repository;
import bin.apply.Replace;
import bin.apply.km.klass.KlassItem;
import bin.exception.MatchException;
import bin.repository.TypeMap;
import work.ReplaceWork;

public class MethodReplace extends ReplaceWork {
    private final DefineMethod method;

    public MethodReplace(DefineMethod method) {
        super(method.getKlassName(), false, method.getParamType());
        if (method.getReturnVarName() == null) {
            String errorMessage = method.getCode().get(method.getEnd());
            throw MatchException.SYSTEM_ERROR.getThrow(errorMessage);
        }
        this.method = method;
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        KlassItem klassItem = (KlassItem) klassValue;
        TypeMap klassRepository = klassItem.getRepository(),
                methodRepository = new TypeMap(),
                deleteRepository;
        Object value;
        try {
            // 1. 클래스 저장소 추가
            // 2. 메소드의 임시 저장소 추가
            Repository.repositoryArray.addFirst(klassRepository);
            Repository.repositoryArray.addFirst(methodRepository);
            // 초기 파라미터 값 넣기
            this.method.setParam(methodRepository, params);
            this.method.start();
            value = Replace.replace(this.method.getReturnVarName());
        } finally {
            // 1. 메소드 임시 저장소 제거
            // 2. 클래스 임시 저장소 제거 및 제거 된 저장소 일치 확인
            Repository.repositoryArray.removeFirst();
            deleteRepository = Repository.repositoryArray.removeFirst();
        }

        if (klassRepository != deleteRepository) {
            String errorMessage = klassRepository.toString()
                    .concat(" != ")
                    .concat(deleteRepository.toString());
            throw MatchException.SYSTEM_ERROR.getThrow(errorMessage);
        }
        return value;
    }

    @Override
    public void reset() {}
}
