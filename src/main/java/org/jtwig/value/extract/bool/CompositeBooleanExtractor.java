package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;

import java.util.Collection;

public class CompositeBooleanExtractor implements BooleanExtractor {
    private final Collection<BooleanExtractor> extractors;

    public CompositeBooleanExtractor(Collection<BooleanExtractor> extractors) {
        this.extractors = extractors;
    }

    @Override
    public Optional<Boolean> extract(Object value) {
        for (BooleanExtractor extractor : extractors) {
            Optional<Boolean> extract = extractor.extract(value);
            if (extract.isPresent()) {
                return extract;
            }
        }
        return Optional.absent();
    }
}
