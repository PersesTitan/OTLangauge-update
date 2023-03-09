package bin.apply.km.klass;

import bin.repository.TypeMap;
import bin.token.KlassToken;
import lombok.Getter;

public class KlassItem {
    @Getter
    private final TypeMap repository = new TypeMap();
    private final DefineKlass defineKlass;

    public KlassItem(DefineKlass defineKlass, Object[] values) {
        this.defineKlass = defineKlass;
        defineKlass.setParam(repository, values);
        defineKlass.create(repository);
    }

    public String getKlassName() {
        return this.defineKlass.getKlassName();
    }
}
