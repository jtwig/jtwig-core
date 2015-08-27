package org.jtwig.value.converter.map;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import java.util.Map;

public class NativeMapConverter extends MapConverter {
    public NativeMapConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value instanceof Map) {
                    return Optional.of(new Value((Map)value));
                }
                return Optional.absent();
            }
        });
    }
}
