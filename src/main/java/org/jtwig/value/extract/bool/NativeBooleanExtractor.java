package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

public class NativeBooleanExtractor implements Extractor<Boolean> {
    @Override
    public Optional<Boolean> extract(Object value) {
        if (value instanceof Boolean) {
            return Optional.of((Boolean) value);
        }
        return Optional.absent();
    }
}
