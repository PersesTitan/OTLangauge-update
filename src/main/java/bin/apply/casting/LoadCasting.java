package bin.apply.casting;

import bin.Repository;
import bin.apply.ReplaceType;
import bin.repository.ReplaceMap;
import bin.token.CastingToken;
import bin.token.KlassToken;
import bin.variable.Types;
import work.ReplaceWork;

public class LoadCasting implements KlassToken, CastingToken {
    public ReplaceMap load() {
        String i = INT_VARIABLE;
        String l = LONG_VARIABLE;
        String b = BOOL_VARIABLE;
        String s = STRING_VARIABLE;
        String c = CHARACTER_VARIABLE;
        String f = FLOAT_VARIABLE;
        String d = DOUBLE_VARIABLE;

        return new ReplaceMap() {{
            put(s, SYSTEM, INTEGER_TO_STRING, new ToString(i));
            put(s, SYSTEM, LONG_TO_STRING, new ToString(l));
            put(s, SYSTEM, BOOLEAN_TO_STRING, new ToString(b));
            put(s, SYSTEM, CHARACTER_TO_STRING, new ToString(c));
            put(s, SYSTEM, FLOAT_TO_STRING, new ToString(f));
            put(s, SYSTEM, DOUBLE_TO_STRING, new ToString(d));

            put(i, SYSTEM, STRING_TO_INTEGER, new StringTo(i));
            put(l, SYSTEM, STRING_TO_LONG, new StringTo(l));
            put(b, SYSTEM, STRING_TO_BOOLEAN, new StringTo(b));
            put(c, SYSTEM, STRING_TO_CHARACTER, new StringTo(c));
            put(f, SYSTEM, STRING_TO_FLOAT, new StringTo(f));
            put(d, SYSTEM, STRING_TO_DOUBLE, new StringTo(d));

            put(i, SYSTEM, CHARACTER_TO_INTEGER, new ReplaceWork(SYSTEM, i, true, c) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (int) ((char) params[0]);
                }
            });
            put(c, SYSTEM, INTEGER_TO_CHARACTER, new ReplaceWork(SYSTEM, c, true, i) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (char) ((int) params[0]);
                }
            });
            put(l, SYSTEM, INTEGER_TO_LONG, new ReplaceWork(SYSTEM, l, true, i) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (long) ((int) params[0]);
                }
            });
            put(f, SYSTEM, INTEGER_TO_FLOAT, new ReplaceWork(SYSTEM, f, true, i) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (float) ((int) params[0]);
                }
            });
            put(d, SYSTEM, INTEGER_TO_DOUBLE, new ReplaceWork(SYSTEM, d, true, i) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (double) ((int) params[0]);
                }
            });
            put(b, SYSTEM, INTEGER_TO_BOOLEAN, new ReplaceWork(SYSTEM, b, true, i) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (int) params[0] != 0;
                }
            });
            put(i, SYSTEM, LONG_TO_INTEGER, new ReplaceWork(SYSTEM, i, true, l) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (int) ((long) params[0]);
                }
            });
            put(f, SYSTEM, LONG_TO_FLOAT, new ReplaceWork(SYSTEM, f, true, l) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (float) ((long) params[0]);
                }
            });
            put(d, SYSTEM, LONG_TO_DOUBLE, new ReplaceWork(SYSTEM, d, true, l) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (double) ((long) params[0]);
                }
            });
            put(b, SYSTEM, LONG_TO_BOOLEAN, new ReplaceWork(SYSTEM, b, true, l) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (long) params[0] != 0L;
                }
            });
            put(i, SYSTEM, FLOAT_TO_INTEGER, new ReplaceWork(SYSTEM, i, true, f) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (int) ((float) params[0]);
                }
            });
            put(l, SYSTEM, FLOAT_TO_LONG, new ReplaceWork(SYSTEM, l, true, f) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (long) ((float) params[0]);
                }
            });
            put(d, SYSTEM, FLOAT_TO_DOUBLE, new ReplaceWork(SYSTEM, d, true, f) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (long) ((double) params[0]);
                }
            });

            put(f, SYSTEM, DOUBLE_TO_FLOAT, new ReplaceWork(SYSTEM, f, true, d) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (float) ((double) params[0]);
                }
            });
            put(l, SYSTEM, DOUBLE_TO_LONG, new ReplaceWork(SYSTEM, l, true, d) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (long) ((double) params[0]);
                }
            });
            put(i, SYSTEM, DOUBLE_TO_INTEGER, new ReplaceWork(SYSTEM, i, true, d) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return (int) ((double) params[0]);
                }
            });
        }};
    }

    private static class ToString extends ReplaceWork implements KlassToken, Repository {
        private ToString(String type) {
            super(SYSTEM, type, true, STRING_VARIABLE);
        }
        @Override
        protected Object replaceItem(Object klassValue, Object[] params) {
            return Types.toString(params[0]);
        }
    }

    private static class StringTo extends ReplaceWork implements KlassToken, Repository {
        private final String type;
        private StringTo(String type) {
            super(SYSTEM, STRING_VARIABLE, true, type);
            this.type = type;
        }
        @Override
        protected Object replaceItem(Object klassValue, Object[] params) {
            return ReplaceType.replace(this.type, params[0].toString());
        }
    }
}
