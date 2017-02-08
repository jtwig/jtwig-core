package org.jtwig.property.strategy.method.convert;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public interface Converter {
    Optional<Value> convert (Object value, Class type);
}
