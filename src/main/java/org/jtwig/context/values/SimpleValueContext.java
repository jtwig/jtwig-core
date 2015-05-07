package org.jtwig.context.values;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

import java.util.Map;

public class SimpleValueContext implements ValueContext {
    private final Map<String, Value> source;

    public SimpleValueContext(Map<String, Value> source) {
        this.source = source;
    }

    @Override
    public Optional<Value> value(String key) {
        return Optional.fromNullable(source.get(key));
    }

    @Override
    public SimpleValueContext add(String key, Object value) {
        source.put(key, new Value(value));
        return this;
    }
}
