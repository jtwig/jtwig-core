package org.jtwig.value.extract.map.selection;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;

import java.util.Map;

public class NativeMapSelectionExtractor implements MapSelectionExtractor {
    @Override
    public Optional<Value> extract(Map map, JtwigValue key) {
        if (map.containsKey(key.asObject())) {
            return Optional.of(new Value(map.get(key.asObject())));
        }
        return Optional.absent();
    }
}
