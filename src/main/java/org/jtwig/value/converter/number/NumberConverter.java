package org.jtwig.value.converter.number;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.converter.TypeSpecificConverter;

import java.math.BigDecimal;

public class NumberConverter extends TypeSpecificConverter {
    public NumberConverter(Function<Object, Optional<Value>> transform) {
        super(BigDecimal.class, transform);
    }
}
