package org.jtwig.functions;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;

public class FunctionArgument {
    private final Optional<String> name;
    private final Object value;

    public FunctionArgument(Optional<String> name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Optional<String> getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s (type: %s, name: %s)", printValue(value), printType(value), printName(name));
    }

    private String printName(Optional<String> name) {
        return name.or("undefined");
    }

    private String printType(Object value) {
        if (value == null) {
            return "undefined";
        } else if (value == Undefined.UNDEFINED) {
            return "undefined";
        } else {
            return String.format("%s", value.getClass().getName());
        }
    }

    private String printValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value == Undefined.UNDEFINED) {
            return "undefined";
        } else {
            return String.format("<%s>", value);
        }
    }
}
