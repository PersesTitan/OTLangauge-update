package bin.variable;

import bin.token.KlassToken;
import bin.token.Token;
import bin.variable.origin.*;
import lombok.Getter;
import work.CreateWork;

@Getter
public enum Types {
    INTEGER (KlassToken.INT_VARIABLE,       KlassToken.SET_INTEGER,
            KlassToken.LIST_INTEGER,        KlassToken.MAP_INTEGER,
            new CreateInteger()),
    LONG    (KlassToken.LONG_VARIABLE,      KlassToken.SET_LONG,
            KlassToken.LIST_LONG,           KlassToken.MAP_LONG,
            new CreateLong()),
    BOOLEAN (KlassToken.BOOL_VARIABLE,      KlassToken.SET_BOOLEAN,
            KlassToken.LIST_BOOLEAN,        KlassToken.MAP_BOOLEAN,
            new CreateBoolean()),
    STRING  (KlassToken.STRING_VARIABLE,    KlassToken.SET_STRING,
            KlassToken.LIST_STRING,         KlassToken.MAP_STRING,
            new CreateString()),
    FLOAT   (KlassToken.CHARACTER_VARIABLE, KlassToken.SET_CHARACTER,
            KlassToken.LIST_CHARACTER,      KlassToken.MAP_CHARACTER,
            new CreateFloat()),
    DOUBLE  (KlassToken.FLOAT_VARIABLE,     KlassToken.SET_FLOAT,
            KlassToken.LIST_FLOAT,          KlassToken.MAP_FLOAT,
            new CreateDouble());

    private final CreateWork<?> createWork;
    private final CreateWork<?> setWork;
    private final CreateWork<?> listWork;
    private CreateWork<?> mapWork;
    private final String originType;
    private final String setType;
    private final String listType;
    private final String mapType;

    Types(String originType, String setType, String listType, String mapType, CreateWork<?> createWork) {
        this.createWork = createWork;
        this.originType = originType;
        this.setType = setType;
        this.listType = listType;
        this.mapType = mapType;
        this.setWork = CreateTool.createSet(this, setType);
        this.listWork = CreateTool.createList(this, listType);
    }

    static {
        for (Types types : Types.values()) {
            types.mapWork = CreateTool.createMap(Types.STRING, types, types.getMapType());
        }
    }

    public static String toString(Object value) {
        return value instanceof Boolean b
                ? (b ? Token.TRUE : Token.FALSE)
                : value.toString();
    }

    public boolean originCheck(String type) {
        return this.originType.equals(type);
    }

    public Object originCast(String value) {
        return this.createWork.create(value);
    }
}
