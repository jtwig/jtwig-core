package org.jtwig.value.extract.map.selection;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;

import java.util.Collection;
import java.util.Map;

public class CompositeMapSelectionExtractor implements MapSelectionExtractor {
    private final Collection<MapSelectionExtractor> mapSelectionExtractors;

    public CompositeMapSelectionExtractor(Collection<MapSelectionExtractor> mapSelectionExtractors) {
        this.mapSelectionExtractors = mapSelectionExtractors;
    }

    @Override
    public Optional<Value> extract(Map map, JtwigValue key) {
        for (MapSelectionExtractor extractor : mapSelectionExtractors) {
            Optional<Value> extract = extractor.extract(map, key);
            if (extract.isPresent()) {
                return extract;
            }
        }
        return Optional.absent();
    }
}
