package org.jtwig.functions.impl.string;

import org.apache.commons.lang3.text.WordUtils;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class TitleFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "title";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);
        String input = request.getArgument(0, String.class);
        return WordUtils.capitalize(input);
    }
}
