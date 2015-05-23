package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

public class ArrayBooleanExtractor implements Extractor<Boolean> {
    @Override
    public Optional<Boolean> extract(Object value) {
        if (value.getClass().isArray()) {
            return Optional.of(((Object[]) value).length > 0);
        }
        return Optional.absent();
    }
}
