package org.jtwig.value.extract.collection;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;

import java.util.Collection;
import java.util.Collections;

public class NullCollectionExtractor implements CollectionExtractor {
    @Override
    public Optional<Collection<Object>> extract(Object value) {
        if (value == null || value == Undefined.UNDEFINED) {
            return Optional.of((Collection<Object>) Collections.emptyList());
        }
        return Optional.absent();
    }
}
