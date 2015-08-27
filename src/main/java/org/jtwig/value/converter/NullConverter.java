package org.jtwig.value.converter;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public class NullConverter implements Converter {
    @Override
    public Optional<Value> convert(Object input, Class to) {
        if (input == null) {
            return Optional.of(new Value(null));
        }
        return Optional.absent();
    }
}
