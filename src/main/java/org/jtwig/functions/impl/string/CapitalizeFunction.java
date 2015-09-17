package org.jtwig.functions.impl.string;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class CapitalizeFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "capitalize";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);
        String input = request.getArgument(0, String.class);
        if (input.length() > 0)
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        else
            return input;
    }
}
