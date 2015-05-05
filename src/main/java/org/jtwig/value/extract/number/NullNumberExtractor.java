package org.jtwig.value.extract.number;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;

import java.math.BigDecimal;

public class NullNumberExtractor implements NumberExtractor {
    @Override
    public Optional<BigDecimal> extract(Object value) {
        if (value == null || value == Undefined.UNDEFINED) {
            return Optional.of(BigDecimal.ZERO);
        }
        return Optional.absent();
    }
}
