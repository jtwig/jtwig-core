package org.jtwig.context.values;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.jtwig.reflection.model.Value;

import java.util.Map;

public class SimpleValueContext implements ValueContext {
    public static final String IDENTIFIER_REGEX = "[a-zA-Z_$][a-zA-Z0-9_$]*";
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
        Preconditions.checkArgument(key.matches(IDENTIFIER_REGEX), String.format("Argument %s isn't a valid identifier. It must conform to the following regex: %s", key, IDENTIFIER_REGEX));
        source.put(key, new Value(value));
        return this;
    }
}
