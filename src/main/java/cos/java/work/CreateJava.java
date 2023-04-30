package cos.java.work;

import work.CreateWork;

public class CreateJava extends CreateWork<Object> {
    public CreateJava(Class<Object> klass, String klassName, String... params) {
        super(klass, klassName, params);
    }

    @Override
    protected Object createItem(Object[] params) {
        return null;
    }
}
