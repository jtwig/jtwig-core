package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.render.RenderRequest;

import java.math.BigDecimal;

public class SubtractOperationCalculator implements SimpleBinaryMathCalculator {
    @Override
    public BigDecimal calculate(RenderRequest request, BigDecimal left, BigDecimal right) {
        return left.subtract(right);
    }
}
