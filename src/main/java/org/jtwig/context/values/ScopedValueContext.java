package org.jtwig.context.values;

import com.google.common.base.Optional;
import org.jtwig.util.JtwigValue;

public class ScopedValueContext implements ValueContext {
    private final ValueContext parent;
    private final ValueContext local;

    public ScopedValueContext(ValueContext parent, ValueContext local) {
        this.parent = parent;
        this.local = local;
    }

    @Override
    public Optional<JtwigValue> value(String key) {
        Optional<JtwigValue> value = local.value(key);
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
