package org.jtwig.value.context;

import org.jtwig.value.Undefined;

public class IsolateParentValueContext implements ValueContext {
    private final ValueContext parent;
    private final ValueContext current;

    public IsolateParentValueContext(ValueContext parent, ValueContext current) {
        this.parent = parent;
        this.current = current;
    }

    @Override
    public Object resolve(String key) {
        Object value = current.resolve(key);
        if (value == Undefined.UNDEFINED) {
            return parent.resolve(key);
        } else {
            return value;
        }
    }

    @Override
    public ValueContext with(String key, Object value) {
        current.with(key, value);
        return this;
    }
}
