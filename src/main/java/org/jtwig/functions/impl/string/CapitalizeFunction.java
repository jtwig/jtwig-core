package org.jtwig.functions.impl.string;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class CapitalizeFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "capitalize";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);
        String input = getString(request, 0);
        if (input.length() > 0)
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        else
            return input;
    }


    private String getString(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(index));
    }
}
