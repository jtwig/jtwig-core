package org.jtwig.value.extract;

import com.google.common.base.Optional;

import java.util.Collection;

public class CompositeExtractor<T> implements Extractor<T> {
    private final Collection<Extractor<T>> extractors;

    public CompositeExtractor(Collection<Extractor<T>> extractors) {
        this.extractors = extractors;
    }

    @Override
    public Optional<T> extract(Object value) {
        for (Extractor<T> extractor : extractors) {
            Optional<T> extract = extractor.extract(value);
            if (extract.isPresent()) {
                return extract;
            }
        }
        return Optional.absent();
    }
}
