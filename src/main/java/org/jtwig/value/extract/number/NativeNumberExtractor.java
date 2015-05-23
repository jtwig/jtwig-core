package org.jtwig.value.extract.number;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

import java.math.BigDecimal;

public class NativeNumberExtractor implements Extractor<BigDecimal> {
    @Override
    public Optional<BigDecimal> extract(Object value) {
        if (value instanceof Number) {
            return Optional.of(new BigDecimal(value.toString()));
        }
        return Optional.absent();
    }
}
