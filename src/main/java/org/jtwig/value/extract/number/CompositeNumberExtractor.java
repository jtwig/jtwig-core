package org.jtwig.value.extract.number;

import com.google.common.base.Optional;

import java.math.BigDecimal;
import java.util.Collection;

public class CompositeNumberExtractor implements NumberExtractor {
    private final Collection<NumberExtractor> extractors;

    public CompositeNumberExtractor(Collection<NumberExtractor> extractors) {
        this.extractors = extractors;
    }

    @Override
    public Optional<BigDecimal> extract(Object value) {
        for (NumberExtractor extractor : extractors) {
            Optional<BigDecimal> extract = extractor.extract(value);
            if (extract.isPresent()) {
                return extract;
            }
        }
        return Optional.absent();
    }
}
