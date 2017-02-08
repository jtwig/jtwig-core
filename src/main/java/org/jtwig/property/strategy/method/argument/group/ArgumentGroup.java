package org.jtwig.property.strategy.method.argument.group;

import com.google.common.base.Optional;
import org.jtwig.property.strategy.method.convert.Converter;
import org.jtwig.reflection.model.Value;

public interface ArgumentGroup {
    Optional<Value> toArgument (Converter converter);
}
