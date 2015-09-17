package org.jtwig.functions.impl.string;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class UpperFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "upper";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);
        String input = request.getArgument(0, String.class);
        return input.toUpperCase();
    }
}
