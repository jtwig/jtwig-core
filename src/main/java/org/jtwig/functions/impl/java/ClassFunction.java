package org.jtwig.functions.impl.java;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;

public class ClassFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "class";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);

        if (request.get(0) == null || request.get(0) == Undefined.UNDEFINED) {
            return Undefined.UNDEFINED;
        } else {
            return request.get(0).getClass();
        }
    }
}
