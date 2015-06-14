package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.GreaterOrEqualOperationCalculator;

public class GreaterOrEqualOperator implements BinaryOperator {
    private static final GreaterOrEqualOperationCalculator CALCULATOR = new GreaterOrEqualOperationCalculator();

    @Override
    public String symbol() {
        return ">=";
    }

    @Override
    public int precedence() {
        return 15;
    }

    @Override
    public BinaryOperationCalculator calculator() {
        return CALCULATOR;
    }
}
