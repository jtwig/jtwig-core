package org.jtwig.value.converter.map;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.converter.TypeSpecificConverter;

import java.util.Map;

public class MapConverter extends TypeSpecificConverter {
    public MapConverter(Function<Object, Optional<Value>> transform) {
        super(Map.class, transform);
    }
}
