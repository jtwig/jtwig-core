package org.jtwig.value.converter.bool;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.converter.Converter;

import java.math.BigDecimal;

public class NumberBooleanConverter extends BooleanConverter {
    public NumberBooleanConverter(final Converter numberExtractor) {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object input) {
                Optional<Value> optional = numberExtractor.convert(input, BigDecimal.class);
                if (optional.isPresent()) {
                    BigDecimal value = optional.get().as(BigDecimal.class);
                    return Optional.of(new Value(value.compareTo(BigDecimal.ZERO) != 0));
                }
                return optional;
            }
        });
    }
}
