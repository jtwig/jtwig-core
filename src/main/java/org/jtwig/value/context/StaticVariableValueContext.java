package org.jtwig.value.context;

public class StaticVariableValueContext implements ValueContext {
    private final ValueContext parent;
    private final String staticKey;
    private final Object staticValue;

    public StaticVariableValueContext(ValueContext parent, String staticKey, Object staticValue) {
        this.parent = parent;
        this.staticKey = staticKey;
        this.staticValue = staticValue;
    }

    @Override
    public Object resolve(String key) {
        if (staticKey.equals(key)) {
            return staticValue;
        } else {
            return parent.resolve(key);
        }
    }

    @Override
    public ValueContext with(String key, Object value) {
        parent.with(key, value);
        return this;
    }
}
