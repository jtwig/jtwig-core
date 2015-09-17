package org.jtwig.functions.impl.logical;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.math.BigDecimal;

public class EvenFunction extends SimpleJtwigFunction {
    public static final BigDecimal TWO = new BigDecimal(2);

    @Override
    public String name() {
        return "even";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        return BigDecimal.ZERO.equals(request.minimumNumberOfArguments(1)
                .maximumNumberOfArguments(1)
                .getArgument(0, BigDecimal.class).remainder(TWO))
                ;
    }
}
