package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.render.RenderRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IntegerMultiplyOperationCalculator implements SimpleBinaryMathCalculator {
    @Override
    public BigDecimal calculate(RenderRequest request, BigDecimal left, BigDecimal right) {
        RoundingMode roundingMode = request.getEnvironment().getValueEnvironment().getRoundingMode();
        return left.setScale(0, roundingMode).multiply(right.setScale(0, roundingMode));
    }
}
