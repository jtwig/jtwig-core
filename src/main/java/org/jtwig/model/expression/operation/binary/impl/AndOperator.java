package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.AndOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;

public class AndOperator implements BinaryOperator {
    private static final AndOperationCalculator CALCULATOR = new AndOperationCalculator();

    @Override
    public String symbol() {
        return "and";
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
