package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;

import java.util.Map;

public class MapBooleanExtractor implements BooleanExtractor {
    @Override
    public Optional<Boolean> extract(Object value) {
        if (value instanceof Map) {
            return Optional.of(!((Map) value).isEmpty());
        }
        return Optional.absent();
    }
}
