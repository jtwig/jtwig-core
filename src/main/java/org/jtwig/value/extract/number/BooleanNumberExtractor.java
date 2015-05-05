package org.jtwig.value.extract.number;

import com.google.common.base.Optional;

import java.math.BigDecimal;

public class BooleanNumberExtractor implements NumberExtractor {
    @Override
    public Optional<BigDecimal> extract(Object value) {
        if (value instanceof Boolean) {
            return Optional.of((Boolean.class.cast(value)) ? BigDecimal.ONE : BigDecimal.ZERO);
        }
        return Optional.absent();
    }
}
