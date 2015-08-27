package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.InOperationCalculator;

public class InOperator implements BinaryOperator {
    private static final InOperationCalculator CALCULATOR = new InOperationCalculator();

    @Override
    public String symbol() {
        return "in";
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
