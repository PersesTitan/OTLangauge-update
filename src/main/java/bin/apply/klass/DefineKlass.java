package bin.apply.klass;

import bin.Repository;
import bin.apply.Read;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.TypeMap;
import lombok.Getter;

import java.util.*;

@Getter
public class DefineKlass {
    private final String klassName;
    private final String[] paramType;
    private final String[] paramName;
    private final Map<Integer, String> code;
    private final int start;
    private final int end;

    public DefineKlass(String klassName, String path, int start, String[][] params) {
        this.code = Repository.codes.get(path);
        this.end = LoopMode.next(this.code, start);
        // 루프 타입 체크
        String endLine = this.code.get(this.end);
        if (!LoopMode.NONE.check(endLine)) throw MatchException.GRAMMAR_ERROR.getThrow(endLine);
        if (params.length == 0) {
            this.paramType = new String[0];
            this.paramName = new String[0];
        } else {
            int len = params[0].length;
            if (len == 1) {
                this.paramType = new String[]{params[0][0]};
                this.paramName = new String[]{params[0][1]};
            } else {
                // 변수명 중복 체크용
                Set<String> set = new HashSet<>();
                String[] paramType = new String[len];
                String[] paramName = new String[len];
                for (int i = 0; i < len; i++) {
                    paramType[i] = params[i][0];
                    paramName[i] = params[i][1];
                    if (set.contains(paramName[i])) throw VariableException.DEFINE_NAME.getThrow(paramName[i]);
                    else set.add(paramName[i]);
                }
                this.paramType = paramType;
                this.paramName = paramName;
            }
        }
        this.klassName = klassName;
        this.start = start + 1;
    }

    public void setParam(TypeMap repository, Object[] values) {
        int len = this.paramType.length;
        if (len == values.length) {
            for (int i = 0; i < len; i++) {
                repository.create(this.paramType[i], this.paramName[i], values[i]);
            }
        } else throw MatchException.PARAM_MATCH_ERROR.getThrow(Integer.toString(values.length));
    }

    public void create(TypeMap repository) {
        Repository.repositoryArray.addFirst(repository);
        try {
            Read.read(this.code, this.start, this.end);
        } finally {
            Repository.repositoryArray.removeFirst();
        }
    }
}
