package org.jtwig.value.extract.string;

import com.google.common.base.Optional;

import java.util.Collection;

public class CompositeStringExtractor implements StringExtractor {
    private final Collection<StringExtractor> extractors;

    public CompositeStringExtractor(Collection<StringExtractor> extractors) {
        this.extractors = extractors;
    }

    @Override
    public Optional<String> extract(Object value) {
        for (StringExtractor extractor : extractors) {
            Optional<String> extract = extractor.extract(value);
            if (extract.isPresent()) {
                return extract;
            }
        }
        return Optional.absent();
    }
}
