package org.jtwig.value.converter.string;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public class BooleanStringConverter extends StringConverter {
    public BooleanStringConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value instanceof Boolean) {
                    return Optional.of(new Value((Boolean) value ? "1" : ""));
                }
                return Optional.absent();
            }
        });
    }
}
