package bin.apply.loop;

import bin.Repository;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.repository.TypeMap;

public class Loop {
    public static void PUT(String endLine, TypeMap repository, LoopFunction loopFunction) {
        // } <= ㅇㅁㅇ 변수명
        String[] tokens = LoopMode.PUT.getToken(endLine).split("\\s", 2);
        String klassType = tokens[0], klassName = tokens[1];

        TypeMap deleteRepository;
        try {
            loopFunction.run();
        } finally {
            deleteRepository = Repository.repositoryArray.removeFirst();
        }

        check(repository, deleteRepository);
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

    public static void SET_KM(TypeMap klassRepository, TypeMap methodRepository,
                              LoopFunction loopFunction) {
        TypeMap deleteRepository;
        try {
            // 1. 클래스 저장소 추가
            // 2. 메소드의 임시 저장소 추가
            Repository.repositoryArray.addFirst(klassRepository);
            Repository.repositoryArray.addFirst(methodRepository);
            // 초기 파라미터 값 넣기
            loopFunction.run();
        } finally {
            // 1. 메소드 임시 저장소 제거
            // 2. 클래스 임시 저장소 제거 및 제거 된 저장소 일치 확인
            Repository.repositoryArray.removeFirst();
            deleteRepository = Repository.repositoryArray.removeFirst();
        }

        check(klassRepository, deleteRepository);
    }

    private static void check(TypeMap a, TypeMap b) {
        if (a != b) {
            String errorMessage = a.toString()
                    .concat(" != ")
                    .concat(b.toString());
            throw MatchException.SYSTEM_ERROR.getThrow(errorMessage);
        }
    }
}
