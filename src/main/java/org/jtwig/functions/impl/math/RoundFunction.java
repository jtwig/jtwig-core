package org.jtwig.functions.impl.math;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.util.FunctionValueUtils;

import java.math.BigDecimal;

public class RoundFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "round";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.maximumNumberOfArguments(2).minimumNumberOfArguments(1);
        if (request.getNumberOfArguments() == 2) {
            String strategy = getString(request, 1);
            switch (RoundStrategy.valueOf(strategy.toUpperCase())) {
                case CEIL:
                    return round(request, BigDecimal.ROUND_CEILING);
                case FLOOR:
                    return round(request, BigDecimal.ROUND_FLOOR);
                default:
                    return round(request, BigDecimal.ROUND_HALF_DOWN);
            }
        }
        return round(request, BigDecimal.ROUND_HALF_DOWN);
    }


    private String getString(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(index));
    }

    private Object round(FunctionRequest request, int mode) {
        BigDecimal conversionResult = FunctionValueUtils.getNumber(request, 0);
        return conversionResult.setScale(0, mode);
    }

    public enum RoundStrategy {
        COMMON,
        CEIL,
        FLOOR
    }
}
