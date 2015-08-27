package org.jtwig.value.converter.bool;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public class NativeBooleanConverter extends BooleanConverter {

    public NativeBooleanConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object input) {
                if (input instanceof Boolean) {
                    return Optional.of(new Value(input));
                }
                return Optional.absent();
            }
        });
    }
}
