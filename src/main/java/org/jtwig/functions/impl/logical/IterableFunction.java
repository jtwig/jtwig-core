package org.jtwig.functions.impl.logical;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.util.Map;

public class IterableFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "iterable";
    }

    @Override
    public Boolean execute(FunctionRequest request) {
        Object input = request.maximumNumberOfArguments(1)
                .minimumNumberOfArguments(1)
                .get(0);

        return input instanceof Iterable
                || input.getClass().isArray()
                || input instanceof Map;
    }
}
