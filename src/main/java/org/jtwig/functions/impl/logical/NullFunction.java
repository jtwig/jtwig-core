package org.jtwig.functions.impl.logical;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class NullFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "null";
    }

    @Override
    public Boolean execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1);
        request.minimumNumberOfArguments(1);
        return request.get(0) == null;
    }
}
