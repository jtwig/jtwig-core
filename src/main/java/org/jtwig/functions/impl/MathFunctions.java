package org.jtwig.functions.impl;

import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;

import java.math.BigDecimal;

public class MathFunctions {
    @JtwigFunction("abs")
    public BigDecimal abs(@Parameter BigDecimal input) {
        return input.abs();
    }

    @JtwigFunction("round")
    public BigDecimal round (@Parameter BigDecimal input, @Parameter String strategy) {
        switch (RoundStrategy.valueOf(strategy.toUpperCase())) {
            case CEIL:
                return input.setScale(0, BigDecimal.ROUND_CEILING);
            case FLOOR:
                return input.setScale(0, BigDecimal.ROUND_FLOOR);
            default:
                return input.setScale(0, BigDecimal.ROUND_HALF_DOWN);
        }
    }

    @JtwigFunction("round")
    public BigDecimal round(@Parameter BigDecimal input) {
        return round(input, RoundStrategy.COMMON.name());
    }


    public static enum RoundStrategy {
        COMMON,
        CEIL,
        FLOOR
    }
}
