package org.jtwig.functions.convert;

import com.google.common.base.Optional;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.model.Value;

import java.math.BigDecimal;

public class BigDecimalToIntegerConverter implements Converter {
    @Override
    public Optional<Value> convert(Object value, Class aClass) {
        if (aClass.equals(Integer.class) || aClass.equals(Integer.TYPE)) {
            if (value instanceof BigDecimal) {
                BigDecimal bigDecimal = (BigDecimal) value;
                return Optional.of(new Value(bigDecimal.intValue()));
            }
        }
        return Optional.absent();
    }
}
