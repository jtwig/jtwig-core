package org.jtwig.value;

import org.jtwig.reflection.convert.CompositeConverter;
import org.jtwig.value.configuration.ValueConfiguration;

public class JtwigValueFactory {

    public static JtwigValue value(Object value, ValueConfiguration configuration) {
        return new JtwigValue(value, new CompositeConverter(configuration.getConverter()), configuration);
    }

    public static JtwigValue undefined(ValueConfiguration configuration) {
        return value(Undefined.UNDEFINED, configuration);
    }
}
