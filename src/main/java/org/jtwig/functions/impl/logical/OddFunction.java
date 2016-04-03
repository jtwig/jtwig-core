package org.jtwig.functions.impl.logical;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.math.BigDecimal;

public class OddFunction extends SimpleJtwigFunction {
    public static final BigDecimal TWO = new BigDecimal(2);
    @Override
    public String name() {
        return "odd";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);

        BigDecimal conversionResult = request.getEnvironment().getValueEnvironment().getNumberConverter().convert(request.get(0))
                .orThrow(request.getPosition(), String.format("Cannot convert %s to number", request.get(0)));

        return BigDecimal.ONE.equals(conversionResult.remainder(TWO));
    }
}
