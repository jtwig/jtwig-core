package org.jtwig.value.context;

public interface ValueContext {
    Object resolve (String key);
    ValueContext with (String key, Object value);
}
