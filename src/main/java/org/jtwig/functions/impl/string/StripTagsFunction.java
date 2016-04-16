package org.jtwig.functions.impl.string;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.util.HtmlUtils;

public class StripTagsFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "striptags";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(2);

        String allowedTags = "";
        if (request.getNumberOfArguments() == 2) {
            allowedTags = getString(request, 1);
        }

        String input = getString(request, 0);

        return HtmlUtils.stripTags(input, allowedTags);
    }

    private String getString(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getStringConverter()
                .convert(request.get(index));
    }
}
