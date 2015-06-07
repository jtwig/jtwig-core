package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.CompositionOperationCalculator;

public class CompositionOperator implements BinaryOperator {
    private static final CompositionOperationCalculator CALCULATOR = new CompositionOperationCalculator();

    @Override
    public String symbol() {
        return "|";
    }

    @Override
    public int precedence() {
        return 100;
    }

    @Override
    public BinaryOperationCalculator calculator() {
        return CALCULATOR;
    }
}
