package org.jtwig.value.converter.bool;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public class IterableBooleanConverter extends BooleanConverter {
    public IterableBooleanConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value instanceof Iterable) {
                    return Optional.of(new Value(((Iterable) value).iterator().hasNext()));
                }
                return Optional.absent();
            }
        });
    }
}
