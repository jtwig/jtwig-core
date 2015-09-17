package org.jtwig.functions.impl.string;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.util.HtmlUtils;

public class StripTagsFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "striptags";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(2);

        String allowedTags = "";
        if (request.getNumberOfArguments() == 2) {
            allowedTags = request.getArgument(1, String.class);
        }

        String input = request.getArgument(0, String.class);

        return HtmlUtils.stripTags(input, allowedTags);
    }
}
