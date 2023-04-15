package bin.variable;

import bin.apply.calculator.number.NumberCalculator;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;
import bin.variable.custom.CustomList;
import bin.variable.custom.CustomMap;
import bin.variable.custom.CustomSet;
import bin.variable.origin.*;
import lombok.Getter;
import work.CreateWork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

@Getter
public enum Types {
    INTEGER     (KlassToken.INT_VARIABLE,       KlassToken.SET_INTEGER,
                KlassToken.LIST_INTEGER,        KlassToken.MAP_INTEGER,
                new CreateInteger()),
    LONG        (KlassToken.LONG_VARIABLE,      KlassToken.SET_LONG,
                KlassToken.LIST_LONG,           KlassToken.MAP_LONG,
                new CreateLong()),
    BOOLEAN     (KlassToken.BOOL_VARIABLE,      KlassToken.SET_BOOLEAN,
                KlassToken.LIST_BOOLEAN,        KlassToken.MAP_BOOLEAN,
                new CreateBoolean()),
    STRING      (KlassToken.STRING_VARIABLE,    KlassToken.SET_STRING,
                KlassToken.LIST_STRING,         KlassToken.MAP_STRING,
                new CreateString()),
    CHARACTER   (KlassToken.CHARACTER_VARIABLE, KlassToken.SET_CHARACTER,
                KlassToken.LIST_CHARACTER,      KlassToken.MAP_CHARACTER,
                new CreateCharacter()),
    FLOAT       (KlassToken.FLOAT_VARIABLE,     KlassToken.SET_FLOAT,
                KlassToken.LIST_FLOAT,          KlassToken.MAP_FLOAT,
                new CreateFloat()),
    DOUBLE      (KlassToken.DOUBLE_VARIABLE,    KlassToken.SET_DOUBLE,
                KlassToken.LIST_DOUBLE,         KlassToken.MAP_DOUBLE,
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

    public boolean isInteger() {
        return this.equals(INTEGER);
    }

    public boolean isLong() {
        return this.equals(LONG);
    }

    public boolean isBoolean() {
        return this.equals(BOOLEAN);
    }

    public boolean isString() {
        return this.equals(STRING);
    }

    public boolean isChar() {
        return this.equals(CHARACTER);
    }

    public boolean isFloat() {
        return this.equals(FLOAT);
    }

    public boolean isDouble() {
        return this.equals(DOUBLE);
    }

    public boolean originCheck(String type) {
        return this.originType.equals(type);
    }

    public Object originCast(String value) {
        return this.createWork.create(value);
    }

    public static String toString(Object value) {
        return value instanceof Boolean b
                ? (b ? Token.TRUE : Token.FALSE)
                : value.toString();
    }

    public static Object toObject(String value, boolean isFirst) {
        if (CheckToken.isSet(value)) return toSet(value);
        else if (CheckToken.isList(value)) return toList(value);
        else if (CheckToken.isMap(value)) return toMap(value);
        else return getTypes(value, isFirst).originCast(value);
    }

    public static Object toObject(String value) {
        return toObject(value, true);
    }

    public static CustomSet<Object> toSet(String value) {
        List<Types> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(value), ",");
        while (tokenizer.hasMoreTokens()) list.add(getTypes(tokenizer.nextToken().strip()));
        return (CustomSet<Object>) getTypes(list).setWork.create(value);
    }

    public static CustomList<Object> toList(String value) {
        List<Types> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(value), ",");
        while (tokenizer.hasMoreTokens()) list.add(getTypes(tokenizer.nextToken().strip()));
        return (CustomList<Object>) getTypes(list).listWork.create(value);
    }

    public static CustomMap<Object, Object> toMap(String value) {
        List<Types> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(value), ",");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().strip();
            if (token.contains(Token.MAP_CENTER)) list.add(getTypes(token.split(Token.MAP_CENTER, 2)[1]));
        }
        return (CustomMap<Object, Object>) getTypes(list).mapWork.create(value);
    }

    private static Types getTypes(Collection<Types> list) {
        // integer, long, boolean, string, character, float, double
        if (list.contains(Types.STRING)) return Types.STRING;
        // integer, long, boolean, character, float, double
        else if (list.contains(Types.BOOLEAN)) {
            if (list.stream().allMatch(v -> v == Types.BOOLEAN)) return Types.BOOLEAN;
            else return Types.STRING;
        // integer, long, character, float, double
        } else if (list.contains(Types.CHARACTER)) {
            if (list.contains(Types.DOUBLE)) return DOUBLE;
            else if (list.contains(Types.FLOAT)) return FLOAT;
            else if (list.contains(Types.LONG)) return LONG;
            else if (list.contains(Types.INTEGER)) return INTEGER;
            else return CHARACTER;
        // integer, long, float, double
        } else if (list.contains(Types.DOUBLE)) return DOUBLE;
        // integer, long, float
        else if (list.contains(Types.FLOAT)) return FLOAT;
        // integer, long
        else if (list.contains(Types.LONG)) return LONG;
        // integer
        else if (list.contains(Types.INTEGER)) return INTEGER;
        else return Types.STRING;
    }

    public static Types getTypes(String value, boolean isFirst) {
        if (value.equals(Token.TRUE) || value.equals(Token.FALSE)) return Types.BOOLEAN;
        else if (CheckToken.isInteger(value)) return Types.INTEGER;
        else if (CheckToken.isLong(value)) return Types.LONG;
        else if (value.length() == 1) return Types.CHARACTER;
        else if (CheckToken.isFloat(value)) return Types.FLOAT;
        else if (CheckToken.isDouble(value)) return Types.DOUBLE;
        else if (Token.compares.stream().anyMatch(value::contains)) return Types.BOOLEAN;
        else if (Token.numbers.stream().anyMatch(value::contains))
            return NumberCalculator.getInstance().getCalculatorTypes(value);
        else if (isFirst) {
            return getTypes(Types.getTypes(value, false));
//            return getTypes(Replace.replace(value, false));
        }
        else return Types.STRING;
    }

    public static Types getTypes(String value) {
        return getTypes(value, true);
    }

    public static Types getTypes(Object value) {
        for (Types types : Types.values()) {
            if (types.createWork.check(value)) return types;
        }
        return Types.STRING;
    }
}
