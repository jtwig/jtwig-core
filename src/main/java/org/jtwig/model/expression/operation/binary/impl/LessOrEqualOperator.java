package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.LessOrEqualOperationCalculator;

public class LessOrEqualOperator implements BinaryOperator {
    private static final LessOrEqualOperationCalculator CALCULATOR = new LessOrEqualOperationCalculator();

    @Override
    public String symbol() {
        return "<=";
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
