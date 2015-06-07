package org.jtwig.model.expression.operation.unary.impl;

import org.jtwig.model.expression.operation.unary.UnaryOperator;
import org.jtwig.model.expression.operation.unary.calculators.NegativeOperationCalculator;
import org.jtwig.model.expression.operation.unary.calculators.UnaryOperationCalculator;

public class NegativeUnaryOperator implements UnaryOperator {
    private static final NegativeOperationCalculator CALCULATOR = new NegativeOperationCalculator();

    @Override
    public String symbol() {
        return "-";
    }

    @Override
    public int precedence() {
        return 0;
    }

    @Override
    public UnaryOperationCalculator calculator() {
        return CALCULATOR;
    }
}
