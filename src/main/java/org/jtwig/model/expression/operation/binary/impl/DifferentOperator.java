package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.DifferentOperationCalculator;

public class DifferentOperator implements BinaryOperator {
    private static final DifferentOperationCalculator CALCULATOR = new DifferentOperationCalculator();

    @Override
    public String symbol() {
        return "!=";
    }

    @Override
    public int precedence() {
        return 20;
    }

    @Override
    public BinaryOperationCalculator calculator() {
        return CALCULATOR;
    }
}
