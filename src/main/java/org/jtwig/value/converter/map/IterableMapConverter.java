package org.jtwig.value.converter.map;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import java.util.LinkedHashMap;
import java.util.Map;

public class IterableMapConverter extends MapConverter {
    public IterableMapConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value instanceof Iterable) {
                    Map result = new LinkedHashMap();
                    int index = 0;
                    for (Object item : ((Iterable) value)) {
                        result.put(index++, item);
                    }
                    return Optional.of(new Value(result));
                }
                return Optional.absent();
            }
        });
    }
}
