package org.jtwig.value.extract.map;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

import java.util.Map;

public class NativeMapExtractor implements Extractor<Map> {
    @Override
    public Optional<Map> extract(Object value) {
        if (value instanceof Map) {
            return Optional.of((Map)value);
        }
        return Optional.absent();
    }
}
