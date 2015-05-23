package org.jtwig.value.extract.number;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;
import org.jtwig.value.extract.Extractor;

import java.math.BigDecimal;

public class NullNumberExtractor implements Extractor<BigDecimal> {
    @Override
    public Optional<BigDecimal> extract(Object value) {
        if (value == null || value == Undefined.UNDEFINED) {
            return Optional.of(BigDecimal.ZERO);
        }
        return Optional.absent();
    }
}
