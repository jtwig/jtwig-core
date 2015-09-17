package org.jtwig.functions.impl.logical;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.util.Map;

public class IterableFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "iterable";
    }

    @Override
    public Boolean execute(JtwigFunctionRequest request) {
        Object input = request.maximumNumberOfArguments(1)
                .minimumNumberOfArguments(1)
                .getArgument(0, Object.class);

        return input instanceof Iterable
                || input.getClass().isArray()
                || input instanceof Map;
    }
}
