package org.jtwig.value.extract.map;

import com.google.common.base.Optional;

import java.util.Map;

public class NativeMapExtractor implements MapExtractor {
    @Override
    public Optional<Map> extract(Object value) {
        if (value instanceof Map) {
            return Optional.of((Map)value);
        }
        return Optional.absent();
    }
}
