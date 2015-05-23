package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

import java.util.Map;

public class MapBooleanExtractor implements Extractor<Boolean> {
    @Override
    public Optional<Boolean> extract(Object value) {
        if (value instanceof Map) {
            return Optional.of(!((Map) value).isEmpty());
        }
        return Optional.absent();
    }
}
