package org.jtwig.context.values;

import com.google.common.base.Optional;

import org.jtwig.util.JtwigValue;

public interface ValueContext {
    Optional<JtwigValue> value (String key);
    ValueContext add (String key, Object value);
}
