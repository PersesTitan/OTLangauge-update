package work;

import bin.Repository;
import bin.exception.SystemException;
import bin.token.KlassToken;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;

public interface ResetWork {
    String s = KlassToken.STRING_VARIABLE;
    String b = KlassToken.BOOL_VARIABLE;
    String i = KlassToken.INT_VARIABLE;
    String f = KlassToken.FLOAT_VARIABLE;
    String l = KlassToken.LONG_VARIABLE;
    String d = KlassToken.DOUBLE_VARIABLE;
    String li = KlassToken.LIST_INTEGER;

    void reset();

    private boolean checkModule(String module) {
        try {
            Class.forName(String.format("cos.%s.Reset", module));
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    default void checkModuleError(String... modules) {
        Set<String> set = Repository.createWorks.values()
                .stream()
                .map(v -> v.getClass())
                .map(Class::getName)
                .filter(v -> v.startsWith("cos."))
                .map(v -> v.substring("cos.".length()))
                .map(v -> v.substring(0, v.indexOf('.')))
                .collect(Collectors.toSet());
        List<String> list = new ArrayList<>();
        for (String module : modules) {
            if (!(this.checkModule(module) && set.contains(module))) list.add(module);
        }
        if (list.isEmpty()) return;
        String errorMessage = String.join(", ", list);
        throw SystemException.IMPORT_ERROR.getThrow(errorMessage);
    }

    @RequiredArgsConstructor
    class AddWork<T> {
        private final String TYPE;
        // ========================== STATIC REPLACE ==========================
        public void addSR(String type, String method, Supplier<Object> supplier) {
            Repository.replaceWorks.put(type, TYPE, method, new ReplaceWork(TYPE, type, true) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return supplier.get();
                }
            });
        }

        public <A> void addSR(String type, String method, String a, BiSupplier<A, Object> supplier) {
            Repository.replaceWorks.put(type, TYPE, method, new ReplaceWork(TYPE, type, true, a) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return supplier.get((A) params[0]);
                }
            });
        }

        public <A, B> void addSR(String type, String method, String a, String b, CiSupplier<A, B, Object> supplier) {
            Repository.replaceWorks.put(type, TYPE, method, new ReplaceWork(TYPE, type, true, a, b) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return supplier.get((A) params[0], (B) params[1]);
                }
            });
        }

        // ========================== STATIC START ==========================
        public void addSS(String method, Con consumer) {
            Repository.startWorks.put(TYPE, method, new StartWork(TYPE, true) {
                @Override
                protected void startItem(Object klassValue, Object[] params) {
                    consumer.accept();
                }
            });
        }

        public <A> void addSS(String method, String a, Consumer<A> consumer) {
            Repository.startWorks.put(TYPE, method, new StartWork(TYPE, true, a) {
                @Override
                protected void startItem(Object klassValue, Object[] params) {
                    consumer.accept((A) params[0]);
                }
            });
        }

        // ========================== REPLACE ==========================
        public void addR(String type, String method, Function<T, Object> function) {
            Repository.replaceWorks.put(type, TYPE, method, new ReplaceWork(TYPE, type, false) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return function.apply((T) klassValue);
                }
            });
        }

        public <A> void addR(String type, String method, String a, BiFunction<T, A, Object> function) {
            Repository.replaceWorks.put(type, TYPE, method, new ReplaceWork(TYPE, type, false, a) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return function.apply((T) klassValue, (A) params[0]);
                }
            });
        }

        public <A, B> void addR(String type, String method, String a, String b, CiFunction<T, A, B, Object> function) {
            Repository.replaceWorks.put(type, TYPE, method, new ReplaceWork(TYPE, type, false, a, b) {
                @Override
                protected Object replaceItem(Object klassValue, Object[] params) {
                    return function.apply((T) klassValue, (A) params[0], (B) params[1]);
                }
            });
        }

        // ========================== START ==========================
        public void addS(String method, Consumer<T> consumer) {
            Repository.startWorks.put(TYPE, method, new StartWork(TYPE, false) {
                @Override
                protected void startItem(Object klassValue, Object[] params) {
                    consumer.accept((T) klassValue);
                }
            });
        }

        public <A> void addS(String method, String a, BiConsumer<T, A> consumer) {
            Repository.startWorks.put(TYPE, method, new StartWork(TYPE, false, a) {
                @Override
                protected void startItem(Object klassValue, Object[] params) {
                    consumer.accept((T) klassValue, (A) params[0]);
                }
            });
        }

        public <A, B> void addS(String method, String a, String b, CiConsumer<T, A, B> consumer) {
            Repository.startWorks.put(TYPE, method, new StartWork(TYPE, false, a, b) {
                @Override
                protected void startItem(Object klassValue, Object[] params) {
                    consumer.accept((T) klassValue, (A) params[0], (B) params[1]);
                }
            });
        }

        @FunctionalInterface
        public interface Con {
            void accept();
        }

        @FunctionalInterface
        public interface BiSupplier<A, B> {
            B get(A a);
        }

        @FunctionalInterface
        public interface CiSupplier<A, B, C> {
            C get(A a, B b);
        }

        @FunctionalInterface
        public interface CiConsumer<T, A, B> {
            void accept(T t, A a, B b);
        }

        @FunctionalInterface
        public interface CiFunction<T, A, B, C> {
            C apply(T t, A a, B b);
        }
    }
}
