package org.jtwig.value.converter.number;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import java.math.BigDecimal;

public class BooleanNumberConverter extends NumberConverter {
    public BooleanNumberConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value instanceof Boolean) {
                    return Optional.of(new Value((Boolean.class.cast(value)) ? BigDecimal.ONE : BigDecimal.ZERO));
                }
                return Optional.absent();
            }
        });
    }
}
