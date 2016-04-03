package org.jtwig.functions.impl.string;

import org.apache.commons.lang3.text.WordUtils;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class TitleFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "title";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);
        String input = request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(0));
        return WordUtils.capitalize(input);
    }
}
