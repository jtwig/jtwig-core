package org.jtwig.value.extract.number;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

import java.math.BigDecimal;

public class ObjectNumberExtractor implements Extractor<BigDecimal> {
    public static final String NUMBER_PATTERN = "^-?\\d*\\.?\\d+$";

    @Override
    public Optional<BigDecimal> extract(Object value) {
        String representation = value.toString();
        if (representation.matches(NUMBER_PATTERN)) {
            return Optional.of(new BigDecimal(representation));
        } else {
            return Optional.absent();
        }
    }
}
