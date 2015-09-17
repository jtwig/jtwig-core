package org.jtwig.functions.impl.logical;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class DefinedFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "defined";
    }

    @Override
    public Boolean execute(JtwigFunctionRequest request) {
        request.maximumNumberOfArguments(1);
        request.minimumNumberOfArguments(1);
        return request.get(0).isDefined();
    }
}
