package org.jtwig.functions.impl.string;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import static java.util.Arrays.asList;

public class SplitFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "split";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(2);
        String input = request.getArgument(0, String.class);
        String separator = request.getArgument(1, String.class);
        return asList(input.split(separator));
    }
}
