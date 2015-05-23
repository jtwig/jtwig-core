package org.jtwig.value.extract.map;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArrayMapExtractor implements Extractor<Map> {
    @Override
    public Optional<Map> extract(Object value) {
        if (value.getClass().isArray()) {
            Map result = new LinkedHashMap();
            int index = 0;
            for (Object item : ((Object[]) value)) {
                result.put(index++, item);
            }
            return Optional.of(result);
        }
        return Optional.absent();
    }
}
