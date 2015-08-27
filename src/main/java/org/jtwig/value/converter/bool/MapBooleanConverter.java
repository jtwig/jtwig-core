package org.jtwig.value.converter.bool;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import java.util.Map;

public class MapBooleanConverter extends BooleanConverter {
    public MapBooleanConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value instanceof Map) {
                    return Optional.of(new Value(!((Map) value).isEmpty()));
                }
                return Optional.absent();
            }
        });
    }
}
