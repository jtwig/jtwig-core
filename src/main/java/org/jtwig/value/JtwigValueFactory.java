package org.jtwig.value;

import org.jtwig.value.environment.ValueEnvironment;

public class JtwigValueFactory {

    public static JtwigValue value(Object value, ValueEnvironment valueEnvironment) {
        return new JtwigValue(value, valueEnvironment);
    }

    public static JtwigValue undefined(ValueEnvironment valueEnvironment) {
        return value(Undefined.UNDEFINED, valueEnvironment);
    }
}
