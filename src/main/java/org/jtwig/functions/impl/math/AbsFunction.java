package org.jtwig.functions.impl.math;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.math.BigDecimal;

public class AbsFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "abs";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);

        BigDecimal conversionResult = request.getEnvironment().getValueEnvironment().getNumberConverter().convert(request.get(0))
                .orThrow(request.getPosition(), String.format("Cannot convert %s to number", request.get(0)));

        return conversionResult.abs();
    }
}
