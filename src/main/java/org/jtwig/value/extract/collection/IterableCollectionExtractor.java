package org.jtwig.value.extract.collection;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

import java.util.ArrayList;
import java.util.Collection;

public class IterableCollectionExtractor implements Extractor<Collection<Object>> {
    @Override
    public Optional<Collection<Object>> extract(Object value) {
        if (value instanceof Iterable) {
            Collection<Object> result = new ArrayList<>();
            Iterable iterable = (Iterable) value;
            for (Object item : iterable) {
                result.add(item);
            }
            return Optional.of(result);
        }
        return Optional.absent();
    }
}
