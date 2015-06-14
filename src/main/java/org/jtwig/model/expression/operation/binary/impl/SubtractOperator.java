package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.SumOperationCalculator;

public class SubtractOperator implements BinaryOperator {
    private static final SumOperationCalculator CALCULATOR = new SumOperationCalculator();

    @Override
    public String symbol() {
        return "-";
    }

    @Override
    public int precedence() {
        return 10;
    }

    @Override
    public BinaryOperationCalculator calculator() {
        return CALCULATOR;
    }
}
