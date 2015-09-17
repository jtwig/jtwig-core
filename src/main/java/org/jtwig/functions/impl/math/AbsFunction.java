package org.jtwig.functions.impl.math;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.math.BigDecimal;

public class AbsFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "abs";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        return request.maximumNumberOfArguments(1).minimumNumberOfArguments(1)
                .getArgument(0, BigDecimal.class).abs();
    }
}
