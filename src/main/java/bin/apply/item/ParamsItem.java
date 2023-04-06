package bin.apply.item;

import bin.apply.ReplaceType;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.variable.Types;

public class ParamsItem {
    public final String[] types;
    public final int size;

    public ParamsItem(String[] types) {
        CheckToken.checkParamType(types);
        this.types = types;
        this.size = types.length;
    }

    public Object[] casting(String params) {
        String[] param = EditToken.cutParams(size, params);
        Object[] values = new Object[size];
        for (int i = 0; i < size; i++) {
            if (Types.STRING.originCheck(types[i])) values[i] = param[i];
            else values[i] = ReplaceType.replace(types[i], param[i]);
        }
        return values;
    }

//    public boolean check(String params) {
//        if ()
//    }

//    public static Object[] getParams(ParamsItem[] items, String params) {
//        if (items.length == 1) return items[0].casting(params);
//        int size = 0;
//
//        for (ParamsItem item : items) {
//
//        }
//    }
}
