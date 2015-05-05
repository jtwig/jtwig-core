package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;

public class IterableBooleanExtractor implements BooleanExtractor {
    @Override
    public Optional<Boolean> extract(Object value) {
        if (value instanceof Iterable) {
            return Optional.of(((Iterable) value).iterator().hasNext());
        }
        return Optional.absent();
    }
}
