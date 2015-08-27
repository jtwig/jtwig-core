package org.jtwig.value.converter.number;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import java.math.BigDecimal;

public class ObjectNumberConverter extends NumberConverter {
    public static final String NUMBER_PATTERN = "^-?\\d*\\.?\\d+$";

    public ObjectNumberConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                String representation = value.toString();
                if (representation.matches(NUMBER_PATTERN)) {
                    return Optional.of(new Value(new BigDecimal(representation)));
                } else {
                    return Optional.absent();
                }
            }
        });
    }
}
