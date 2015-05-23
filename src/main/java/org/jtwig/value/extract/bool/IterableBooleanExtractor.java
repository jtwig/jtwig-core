package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

public class IterableBooleanExtractor implements Extractor<Boolean> {
    @Override
    public Optional<Boolean> extract(Object value) {
        if (value instanceof Iterable) {
            return Optional.of(((Iterable) value).iterator().hasNext());
        }
        return Optional.absent();
    }
}
