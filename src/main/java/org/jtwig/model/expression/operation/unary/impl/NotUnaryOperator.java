package org.jtwig.model.expression.operation.unary.impl;

import org.jtwig.model.expression.operation.unary.UnaryOperator;
import org.jtwig.model.expression.operation.unary.calculators.NotOperationCalculator;
import org.jtwig.model.expression.operation.unary.calculators.UnaryOperationCalculator;

public class NotUnaryOperator implements UnaryOperator {
    private static final NotOperationCalculator CALCULATOR = new NotOperationCalculator();

    @Override
    public String symbol() {
        return "not";
    }

    @Override
    public int precedence() {
        return 0;
    }

    @Override
    public UnaryOperationCalculator calculator() {
        return CALCULATOR;
    }
}
