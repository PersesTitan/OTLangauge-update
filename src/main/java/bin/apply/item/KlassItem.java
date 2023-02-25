package bin.apply.item;

import bin.apply.klass.DefineKlass;
import bin.repository.TypeMap;
import lombok.Getter;

public class KlassItem {
    private final TypeMap repository = new TypeMap();
    private final DefineKlass defineKlass;

    public KlassItem(DefineKlass defineKlass, Object[] values) {
        defineKlass.setParam(this.repository, values);
        this.defineKlass = defineKlass;
        defineKlass.create(this.repository);
    }

    public String getKlassName() {
        return this.defineKlass.getKlassName();
    }
}
