package org.jtwig.value.converter.bool;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.converter.TypeSpecificConverter;

public class BooleanConverter extends TypeSpecificConverter {
    public BooleanConverter(Function<Object, Optional<Value>> transform) {
        super(Boolean.class, transform);
    }
}
