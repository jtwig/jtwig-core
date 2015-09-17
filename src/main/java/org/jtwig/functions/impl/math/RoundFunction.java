package org.jtwig.functions.impl.math;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.math.BigDecimal;

public class RoundFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "round";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.maximumNumberOfArguments(2).minimumNumberOfArguments(1);
        if (request.getNumberOfArguments() == 2) {
            String strategy = request.getArgument(1, String.class);
            switch (RoundStrategy.valueOf(strategy.toUpperCase())) {
                case CEIL:
                    return round(request, BigDecimal.ROUND_CEILING);
                case FLOOR:
                    return round(request, BigDecimal.ROUND_FLOOR);
            }
        }
        return round(request, BigDecimal.ROUND_HALF_DOWN);
    }

    private Object round(JtwigFunctionRequest request, int mode) {
        BigDecimal input = request.getArgument(0, BigDecimal.class);
        return input.setScale(0, mode);
    }


    public static enum RoundStrategy {
        COMMON,
        CEIL,
        FLOOR
    }
}
