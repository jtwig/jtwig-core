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
        request.maximumNumberOfArguments(3).minimumNumberOfArguments(1);
        int precision = 0;
        RoundStrategy roundingMode = RoundStrategy.COMMON;

        if (request.getNumberOfArguments() == 3) {
            precision = FunctionValueUtils.getNumber(request, 1).intValue();
            roundingMode = RoundStrategy.valueOf(FunctionValueUtils.getString(request, 2).toUpperCase());
        }

        if (request.getNumberOfArguments() == 2) {
            roundingMode = RoundStrategy.valueOf(FunctionValueUtils.getString(request, 1).toUpperCase());
        }

        BigDecimal number = FunctionValueUtils.getNumber(request, 0);
        return roundingMode.round(number, precision);
    }


    public enum RoundStrategy {
        COMMON(new StaticRound(BigDecimal.ROUND_HALF_UP)),
        CEIL(new StaticRound(BigDecimal.ROUND_CEILING)),
        FLOOR(new StaticRound(BigDecimal.ROUND_FLOOR));

        private Round round;

        RoundStrategy(Round round) {
            this.round = round;
        }

        public BigDecimal round (BigDecimal number, int precision) {
            return round.round(number, precision);
        }
    }

    private interface Round {
        BigDecimal round (BigDecimal number, int precision);
    }

    private static class StaticRound implements Round {
        private final int roundingMode;

        StaticRound(int roundingMode) {
            this.roundingMode = roundingMode;
        }

        @Override
        public BigDecimal round(BigDecimal number, int precision) {
            return number.setScale(precision, roundingMode);
        }
    }
}
