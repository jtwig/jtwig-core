package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;

public class DefaultFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "default";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(2);
        Object input = request.get(0);
        if (input == null || input == Undefined.UNDEFINED)
            return request.get(1);
        else
            return input;
    }
}
