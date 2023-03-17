package bin.system.etc;

import bin.Repository;
import bin.token.KlassToken;
import work.ReplaceWork;

public class GetType extends ReplaceWork {
    public GetType() {
        super(KlassToken.SYSTEM, false, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object replaceItem(Object klassValue, Object[] params) {
        String name = params[0].toString().strip();
        return Repository.repositoryArray.getMap(name).getKlassType();
    }
}
