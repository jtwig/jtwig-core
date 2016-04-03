package org.jtwig.functions.impl.string;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class LowerFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "lower";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);
        String input = request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(0));
        return input.toLowerCase();
    }
}
