package org.jtwig.context.values;

import com.google.common.base.Optional;

import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;

public interface ValueContext {
    Optional<Value> value (String key);
    ValueContext add (String key, Object value);
}
