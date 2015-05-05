package org.jtwig.value.extract.map;

import com.google.common.base.Optional;

import java.util.LinkedHashMap;
import java.util.Map;

public class IterableMapExtractor implements MapExtractor {
    @Override
    public Optional<Map> extract(Object value) {
        if (value instanceof Iterable) {
            Map result = new LinkedHashMap();
            int index = 0;
            for (Object item : ((Iterable) value)) {
                result.put(index++, item);
            }
            return Optional.of(result);
        }
        return Optional.absent();
    }
}
