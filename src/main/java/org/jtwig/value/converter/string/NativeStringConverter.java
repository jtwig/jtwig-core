package org.jtwig.value.converter.string;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public class NativeStringConverter extends StringConverter {
    public NativeStringConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                return Optional.of(new Value(value.toString()));
            }
        });
    }
}
