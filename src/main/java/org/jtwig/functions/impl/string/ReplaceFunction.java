package org.jtwig.functions.impl.string;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.util.Map;

public class ReplaceFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "replace";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(2);
        String input = request.getArgument(0, String.class);
        Map replacements = request.getArgument(0, Map.class);

        for (Object key : replacements.keySet()) {
            input = input.replace(key.toString(), replacements.get(key).toString());
        }
        return input;
    }
}
