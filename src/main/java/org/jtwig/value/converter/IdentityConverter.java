package org.jtwig.value.converter;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public class IdentityConverter implements Converter {
    @Override
    public Optional<Value> convert(Object input, Class to) {
        if (input != null && to.isAssignableFrom(input.getClass())) {
            return Optional.of(new Value(input));
        } else {
            return Optional.absent();
        }
    }
}
