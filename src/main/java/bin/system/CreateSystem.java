package bin.system;

import bin.exception.VariableException;
import bin.token.KlassToken;
import work.CreateWork;

public class CreateSystem extends CreateWork<System> {
    public CreateSystem() {
        super(System.class, KlassToken.SYSTEM);
    }

    @Override
    protected Object createItem(Object[] params) {
        throw VariableException.SYSTEM_KLASS_USE.getThrow(null);
    }

    @Override
    public void reset() {}
}
