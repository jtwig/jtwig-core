package org.jtwig.value.converter;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public interface Converter {
    Optional<Value> convert(Object input, Class to);
}
