package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;

public class DefaultBooleanExtractor implements BooleanExtractor {
    private final Boolean defaultValue;

    public DefaultBooleanExtractor(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Optional<Boolean> extract(Object value) {
        return Optional.of(defaultValue);
    }
}
