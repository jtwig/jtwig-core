package org.jtwig.functions.impl.string;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class FormatFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "format";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1);
        String input = request.getArgument(0, String.class);
        return String.format(input, request.getRemainingArguments(1));
    }
}
