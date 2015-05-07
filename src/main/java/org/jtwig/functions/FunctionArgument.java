package org.jtwig.functions;

import com.google.common.base.Optional;

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
        if (name.isPresent()) {
            return String.format("<%s> (%s)", value, name.get());
        } else {
            return String.format("<%s>", value.toString());
        }
    }
}
