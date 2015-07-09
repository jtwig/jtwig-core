package org.jtwig.value.converter.map;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;

import java.util.Collections;
import java.util.Map;

public class NullMapConverter extends MapConverter {
    public NullMapConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value == null || value == Undefined.UNDEFINED) {
                    return Optional.of(new Value(Collections.emptyMap()));
                }
                return Optional.absent();
            }
        });
    }
}
