package org.jtwig.functions.impl.string;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import static java.util.Arrays.asList;

public class SplitFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "split";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(2);
        String input = getString(request, 0);
        String separator = getString(request, 1);
        return asList(input.split(separator));
    }

    private String getString(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(index));
    }
}
