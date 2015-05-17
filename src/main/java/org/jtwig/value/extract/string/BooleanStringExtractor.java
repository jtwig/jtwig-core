package org.jtwig.value.extract.string;

import com.google.common.base.Optional;

public class BooleanStringExtractor implements StringExtractor {

    @Override
    public Optional<String> extract(Object value) {
        if (value instanceof Boolean) {
            return Optional.of((Boolean) value ? "1" : "");
        }
        return Optional.absent();
    }
}
