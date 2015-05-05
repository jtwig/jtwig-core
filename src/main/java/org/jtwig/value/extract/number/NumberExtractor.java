package org.jtwig.value.extract.number;

import com.google.common.base.Optional;

import java.math.BigDecimal;

public interface NumberExtractor {
    Optional<BigDecimal> extract (Object value);
}
