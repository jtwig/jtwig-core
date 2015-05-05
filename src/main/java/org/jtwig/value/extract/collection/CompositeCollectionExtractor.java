package org.jtwig.value.extract.collection;

import com.google.common.base.Optional;

import java.util.Collection;

public class CompositeCollectionExtractor implements CollectionExtractor {
    private final Collection<CollectionExtractor> extractors;

    public CompositeCollectionExtractor(Collection<CollectionExtractor> extractors) {
        this.extractors = extractors;
    }

    @Override
    public Optional<Collection<Object>> extract(Object value) {
        for (CollectionExtractor extractor : extractors) {
            Optional<Collection<Object>> extract = extractor.extract(value);
            if (extract.isPresent()) {
                return extract;
            }
        }
        return Optional.absent();
    }
}
