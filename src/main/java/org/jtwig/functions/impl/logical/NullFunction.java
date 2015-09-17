package org.jtwig.functions.impl.logical;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class NullFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "null";
    }

    @Override
    public Boolean execute(JtwigFunctionRequest request) {
        request.maximumNumberOfArguments(1);
        request.minimumNumberOfArguments(1);
        return request.get(0).isNull();
    }
}
