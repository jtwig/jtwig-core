package org.jtwig.functions;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.Undefined;

public class FunctionArgument {
    private final Optional<String> name;
    private final JtwigValue value;

    public FunctionArgument(Optional<String> name, JtwigValue value) {
        this.name = name;
        this.value = value;
    }

    public Optional<String> getName() {
        return name;
    }

    public JtwigValue getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s (type: %s, name: %s)", printValue(value), printType(value), printName(name));
    }

    private String printName(Optional<String> name) {
        return name.or("undefined");
    }

    private String printType(JtwigValue value) {
        if (value.isNull()) {
            return "undefined";
        } else if (!value.isDefined()) {
            return "undefined";
        } else {
            return String.format("%s", value.asObject().getClass().getName());
        }
    }

    private String printValue(JtwigValue value) {
        if (value.isNull()) {
            return "null";
        } else if (!value.isDefined()) {
            return "undefined";
        } else {
            return String.format("<%s>", value.asObject());
        }
    }
}
