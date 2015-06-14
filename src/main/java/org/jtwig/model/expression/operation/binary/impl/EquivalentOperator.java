package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.EquivalentOperationCalculator;

public class EquivalentOperator implements BinaryOperator {
    private static final EquivalentOperationCalculator CALCULATOR = new EquivalentOperationCalculator();

    @Override
    public String symbol() {
        return "==";
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
