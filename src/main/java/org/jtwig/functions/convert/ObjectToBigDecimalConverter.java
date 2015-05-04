package org.jtwig.functions.convert;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.model.Value;
import org.jtwig.util.JtwigValue;

import java.math.BigDecimal;

public class ObjectToBigDecimalConverter implements Converter {
    @Override
    public Optional<Value> convert(Object value, Class aClass) {
        if (aClass.equals(BigDecimal.class)) {
            return new JtwigValue(value).asNumber()
                    .transform(new Function<BigDecimal, Value>() {
                        @Override
                        public Value apply(BigDecimal input) {
                            return new Value(input);
                        }
                    });
        }
        return Optional.absent();
    }
}
