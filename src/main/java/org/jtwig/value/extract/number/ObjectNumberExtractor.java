package org.jtwig.value.extract.number;

import com.google.common.base.Optional;

import java.math.BigDecimal;

public class ObjectNumberExtractor implements NumberExtractor {
    @Override
    public Optional<BigDecimal> extract(Object value) {
        String representation = value.toString();
        if (representation.matches("^-?\\d*\\.?\\d+$")) {
            return Optional.of(new BigDecimal(representation));
        } else {
            return Optional.absent();
        }
    }
}
