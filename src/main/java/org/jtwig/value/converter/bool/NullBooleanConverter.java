package org.jtwig.value.converter.bool;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;

public class NullBooleanConverter extends BooleanConverter {
    public NullBooleanConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value == null || value == Undefined.UNDEFINED) {
                    return Optional.of(new Value(false));
                }
                return Optional.absent();
            }
        });
    }
}
