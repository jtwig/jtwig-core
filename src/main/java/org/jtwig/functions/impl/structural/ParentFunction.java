package org.jtwig.functions.impl.structural;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class ParentFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "parent";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(0);
        request.maximumNumberOfArguments(0);

        return '';
    }
}
