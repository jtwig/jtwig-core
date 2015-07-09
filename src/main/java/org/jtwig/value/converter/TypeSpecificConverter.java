package org.jtwig.value.converter;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public class TypeSpecificConverter implements Converter {
    private final Class type;
    private final Function<Object, Optional<Value>> transform;

    public TypeSpecificConverter(Class type, Function<Object, Optional<Value>> transform) {
        this.type = type;
        this.transform = transform;
    }

    @Override
    public Optional<Value> convert(Object input, Class to) {
        if (to.equals(type)) {
            return transform.apply(input);
        }
        return Optional.absent();
    }
}
