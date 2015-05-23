package org.jtwig.value.extract.number;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

import java.math.BigDecimal;

public class BooleanNumberExtractor implements Extractor<BigDecimal> {
    @Override
    public Optional<BigDecimal> extract(Object value) {
        if (value instanceof Boolean) {
            return Optional.of((Boolean.class.cast(value)) ? BigDecimal.ONE : BigDecimal.ZERO);
        }
        return Optional.absent();
    }
}
