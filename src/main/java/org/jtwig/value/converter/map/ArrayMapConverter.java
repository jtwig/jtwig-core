package org.jtwig.value.converter.map;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArrayMapConverter extends MapConverter {
    public ArrayMapConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value.getClass().isArray()) {
                    Map result = new LinkedHashMap();
                    int index = 0;
                    for (Object item : ((Object[]) value)) {
                        result.put(index++, item);
                    }
                    return Optional.of(new Value(result));
                }
                return Optional.absent();
            }
        });
    }
}
