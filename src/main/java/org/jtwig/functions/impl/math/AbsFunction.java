package org.jtwig.functions.impl.math;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.util.FunctionValueUtils;

import java.math.BigDecimal;

public class AbsFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "abs";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);

        BigDecimal conversionResult = FunctionValueUtils.getNumber(request, 0);
        return conversionResult.abs();
    }
}
