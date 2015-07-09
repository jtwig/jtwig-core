package org.jtwig.value.converter.number;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import java.math.BigDecimal;

public class NativeNumberConverter extends NumberConverter {
    public NativeNumberConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value instanceof Number) {
                    return Optional.of(new Value(new BigDecimal(value.toString())));
                }
                return Optional.absent();
            }
        });
    }
}
