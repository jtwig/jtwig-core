package org.jtwig.property.strategy.method;

import org.jtwig.reflection.model.java.JavaMethod;

import java.util.List;

public class MethodArgumentsMatcher {
    private final ArgumentsConverter argumentsConverter;

    public MethodArgumentsMatcher(ArgumentsConverter argumentsConverter) {
        this.argumentsConverter = argumentsConverter;
    }

    public boolean matches(JavaMethod method, List<Object> arguments) {
        return argumentsConverter.convert(method, arguments.toArray()).isPresent();
    }
}
