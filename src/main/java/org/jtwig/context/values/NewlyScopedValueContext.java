package org.jtwig.context.values;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public class NewlyScopedValueContext implements ValueContext {
    private final ValueContext parent;
    private final ValueContext local;

    public NewlyScopedValueContext(ValueContext parent, ValueContext local) {
        this.parent = parent;
        this.local = local;
    }

    @Override
    public Optional<Value> value(String key) {
        return local.value(key)
            .or(parent.value(key));
    }

    @Override
    public NewlyScopedValueContext add(String key, Object value) {
        if (parent.value(key).isPresent()) {
            parent.add(key, value);
        } else {
            local.add(key, value);
        }
        return this;
    }
}
