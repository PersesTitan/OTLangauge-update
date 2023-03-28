package bin.list;

import bin.Repository;
import bin.apply.Read;
import bin.apply.mode.LoopMode;
import bin.exception.VariableException;
import bin.repository.code.CodeMap;
import bin.variable.custom.CustomSet;
import work.LoopWork;

import java.util.Iterator;

public class SetForEach extends LoopWork {
    public SetForEach(String klassType) {
        super(LoopMode.PUT, klassType, false);
    }

    @Override
    protected int loopItem(int start, int end, LoopMode mode, CodeMap code,
                           String repoKlass, Object klassValue, Object[] params) {
        CustomSet<?> set = (CustomSet<?>) klassValue;
        // ex) } <= ㅇㅁㅇ 변수명
        String[] tokens = mode.getToken(code.get(end)).split("\\s", 2);
        String type = tokens[0].strip();    // ㅇㅁㅇ
        String name = tokens[1].strip();    // 변수명
        if (!set.getTypes().getOriginType().equals(type))
            throw VariableException.TYPE_ERROR.getThrow(set.getTypes().getOriginType() + " != " + type);
        Iterator<?> iterator = set.iterator();
        if (iterator.hasNext()) {
            Repository.repositoryArray.create(type, name, iterator.next());
            try {
                while (iterator.hasNext()) {
                    Repository.repositoryArray.update(type, name, iterator.next());
                    Read.read(code, start, end, repoKlass);
                }
            } finally {
                Repository.repositoryArray.remove(type, name);
            }
        }
        return end;
    }
}
