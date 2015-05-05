package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;

public class NativeBooleanExtractor implements BooleanExtractor {
    @Override
    public Optional<Boolean> extract(Object value) {
        if (value instanceof Boolean) {
            return Optional.of((Boolean) value);
        }
        return Optional.absent();
    }
}
