package org.jtwig.functions.impl.logical;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;

public class DefinedFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "defined";
    }

    @Override
    public Boolean execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1);
        request.minimumNumberOfArguments(1);

        return request.get(0) != Undefined.UNDEFINED;
    }
}
