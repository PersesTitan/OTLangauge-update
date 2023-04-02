package bin.apply.loop;

import bin.Repository;
import bin.apply.ReplaceType;
import bin.apply.km.klass.KlassItem;
import bin.apply.km.method.DefineMethod;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.exception.SystemException;
import bin.repository.TypeMap;

import java.util.Iterator;

public class Loop {
    public static <T> void PUT(String endLine, Iterable<T> iterable, LoopFunction loopFunction) {
        // } <= ㅇㅁㅇ 변수명
        String[] tokens = LoopMode.PUT.getToken(endLine).split("\\s", 2);
        PUT(tokens[0].strip(), tokens[1].strip(), iterable, loopFunction);
    }

    public static <T> void PUT(String klassType, String klassName,
                               Iterable<T> iterable, LoopFunction loopFunction) {
        Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            Repository.repositoryArray.create(klassType, klassName, iterator.next());
            loopFunction.run();
            try {
                while (iterator.hasNext()) {
                    Repository.repositoryArray.update(klassType, klassName, iterator.next());
                    loopFunction.run();
                }
            } finally {
                // 추가 했던 변수 제거
                Repository.repositoryArray.remove(klassType, klassName);
            }
        }
    }

    public static Object RETURN(String variableName, TypeMap repository, LoopFunction loopFunction) {
        // } => 변수명
        TypeMap deleteRepository;
        Object value;
        try {
            Repository.repositoryArray.addFirst(repository);
            loopFunction.run();
            value = Repository.repositoryArray.find(variableName);
        } finally {
            deleteRepository = Repository.repositoryArray.removeFirst();
        }

        check(repository, deleteRepository);
        return value;
    }

    public static Object startMethod(DefineMethod method, KlassItem klassItem, Object[] params) {
        TypeMap methodRepository = new TypeMap();
        try {
            // 1. 클래스 저장소 추가
            // 2. 메소드의 임시 저장소 추가
            if (klassItem != null) Repository.repositoryArray.addFirst(klassItem.getRepository());
            Repository.repositoryArray.addFirst(methodRepository);
            // 초기 파라미터 값 넣기
            method.setParam(methodRepository, params == null ? new Object[0] : params);
            method.start();
            if (method.getReturnVarName() == null) return null;
            else return ReplaceType.replace(method.getReturnType(), method.getReturnVarName());
        } finally {
            // 1. 메소드 임시 저장소 제거
            // 2. 클래스 임시 저장소 제거 및 제거 된 저장소 일치 확인
            check(Repository.repositoryArray.removeFirst(), methodRepository);
            if (klassItem != null) check(Repository.repositoryArray.removeFirst(), klassItem.getRepository());
        }
    }

    public static void check(TypeMap a, TypeMap b) {
        if (a != b) throw SystemException.SYSTEM_ERROR.getThrow(a + " != " + b);
    }
}
