package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.render.RenderRequest;

import java.math.BigDecimal;

public interface SimpleBinaryMathCalculator {
    BigDecimal calculate(RenderRequest request, BigDecimal left, BigDecimal right);
}
