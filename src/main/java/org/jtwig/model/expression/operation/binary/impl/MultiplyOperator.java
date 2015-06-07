package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.MultiplyOperationCalculator;

public class MultiplyOperator implements BinaryOperator {
    private static final MultiplyOperationCalculator CALCULATOR = new MultiplyOperationCalculator();

    @Override
    public String symbol() {
        return "*";
    }

    @Override
    public int precedence() {
        return 30;
    }

    @Override
    public BinaryOperationCalculator calculator() {
        return CALCULATOR;
    }
}
