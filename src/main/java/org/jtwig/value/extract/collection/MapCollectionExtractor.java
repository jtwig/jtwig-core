package org.jtwig.value.extract.collection;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

import java.util.Collection;
import java.util.Map;

public class MapCollectionExtractor implements Extractor<Collection<Object>> {
    @Override
    public Optional<Collection<Object>> extract(Object value) {
        if (value instanceof Map) {
            return Optional.of((Collection<Object>) (((Map) value).values()));
        }
        return Optional.absent();
    }
}
