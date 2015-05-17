package org.jtwig.value.extract.string;

import com.google.common.base.Optional;

public class NativeStringExtractor implements StringExtractor {
    @Override
    public Optional<String> extract(Object value) {
        return Optional.of(value.toString());
    }
}
