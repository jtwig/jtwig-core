package org.jtwig.value.converter.bool;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public class ArrayBooleanConverter extends BooleanConverter {
    public ArrayBooleanConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object input) {
                if (input.getClass().isArray()) {
                    return Optional.of(new Value(((Object[]) input).length > 0));
                }
                return Optional.absent();
            }
        });
    }
}
