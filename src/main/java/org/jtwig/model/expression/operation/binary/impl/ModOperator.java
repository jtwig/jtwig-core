package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.ModOperationCalculator;

public class ModOperator implements BinaryOperator {
    private static final ModOperationCalculator CALCULATOR = new ModOperationCalculator();

    @Override
    public String symbol() {
        return "%";
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
