package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.GreaterOperationCalculator;

public class GreaterOperator implements BinaryOperator {
    private static final GreaterOperationCalculator CALCULATOR = new GreaterOperationCalculator();

    @Override
    public String symbol() {
        return ">";
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
