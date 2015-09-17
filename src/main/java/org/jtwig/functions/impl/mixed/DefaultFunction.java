package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;

public class DefaultFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "default";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(2);
        Object input = request.getArgument(0, Object.class);
        if (input == null || input.equals(Undefined.UNDEFINED))
            return request.getArgument(1, Object.class);
        else
            return input;
    }
}
