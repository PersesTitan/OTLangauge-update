package bin.apply.km.klass;

import work.CreateWork;

public class CreateKlass extends CreateWork<KlassItem> {
    private final DefineKlass defineKlass;

    public CreateKlass(DefineKlass defineKlass) {
        super(KlassItem.class, defineKlass.getKlassName(), defineKlass.getParamType());
        this.defineKlass = defineKlass;
    }

    @Override
    protected Object createItem(Object[] params) {
        return new KlassItem(this.defineKlass, params);
    }

    @Override
    public boolean check(Object value) {
        return value instanceof KlassItem klassItem
                && klassItem.getKlassName().equals(this.defineKlass.getKlassName());
    }

    @Override
    public void reset() {}
}
