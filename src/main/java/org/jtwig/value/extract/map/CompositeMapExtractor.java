package org.jtwig.value.extract.map;

import com.google.common.base.Optional;

import java.util.Collection;
import java.util.Map;

public class CompositeMapExtractor implements MapExtractor {
    private final Collection<MapExtractor> extractors;

    public CompositeMapExtractor(Collection<MapExtractor> extractors) {
        this.extractors = extractors;
    }

    @Override
    public Optional<Map> extract(Object value) {
        for (MapExtractor extractor : extractors) {
            Optional<Map> extract = extractor.extract(value);
            if (extract.isPresent()) {
                return extract;
            }
        }
        return Optional.absent();
    }
}
