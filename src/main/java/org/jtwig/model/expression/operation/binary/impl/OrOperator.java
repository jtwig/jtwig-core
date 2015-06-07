package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.OrOperationCalculator;

public class OrOperator implements BinaryOperator {
    private static final OrOperationCalculator CALCULATOR = new OrOperationCalculator();

    @Override
    public String symbol() {
        return "or";
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
