package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;

public class StringBooleanExtractor implements BooleanExtractor {
    @Override
    public Optional<Boolean> extract(Object value) {
        if (value instanceof CharSequence) {
            return Optional.of(((CharSequence) value).length() > 0);
        }
        return Optional.absent();
    }
}
