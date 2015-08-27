package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.LessOperationCalculator;

public class LessOperator implements BinaryOperator {
    private static final LessOperationCalculator CALCULATOR = new LessOperationCalculator();

    @Override
    public String symbol() {
        return "<";
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
