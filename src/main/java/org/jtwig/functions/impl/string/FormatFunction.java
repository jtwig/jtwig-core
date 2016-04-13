package org.jtwig.functions.impl.string;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class FormatFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "format";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1);
        String input = getString(request, 0);
        return String.format(input, request.getRemainingArguments(1));
    }


    private String getString(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment()
                .getStringConverter().convert(request.get(index));
    }
}
