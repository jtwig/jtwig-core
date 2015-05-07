package org.jtwig.context.values;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;

public class ScopedValueContext implements ValueContext {
    private final ValueContext parent;
    private final ValueContext local;

    public ScopedValueContext(ValueContext parent, ValueContext local) {
        this.parent = parent;
        this.local = local;
    }

    @Override
    public Optional<Value> value(String key) {
        Optional<Value> value = local.value(key);
        if (!value.isPresent()) {
            return parent.value(key);
        } else {
            return value;
        }
    }

    @Override
    public ScopedValueContext add(String key, Object value) {
        local.add(key, value);
        return this;
    }
}
