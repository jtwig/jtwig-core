package org.jtwig.value.converter.number;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;

import java.math.BigDecimal;

public class NullNumberConverter extends NumberConverter {
    public NullNumberConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value == null || value == Undefined.UNDEFINED) {
                    return Optional.of(new Value(BigDecimal.ZERO));
                }
                return Optional.absent();
            }
        });
    }
}
