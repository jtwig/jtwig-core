package org.jtwig.value.extract.collection;

import com.google.common.base.Optional;

import java.util.Collection;

import static java.util.Arrays.asList;

public class ArrayCollectionExtractor implements CollectionExtractor {
    @Override
    public Optional<Collection<Object>> extract(Object value) {
        if (value.getClass().isArray()) {
            return Optional.of((Collection<Object>) asList((Object[]) value));
        }
        return Optional.absent();
    }
}
