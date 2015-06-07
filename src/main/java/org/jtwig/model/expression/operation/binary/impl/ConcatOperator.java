package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.ConcatOperationCalculator;

public class ConcatOperator implements BinaryOperator {
    private static final ConcatOperationCalculator CALCULATOR = new ConcatOperationCalculator();

    @Override
    public String symbol() {
        return "~";
    }

    @Override
    public int precedence() {
        return 2;
    }

    @Override
    public BinaryOperationCalculator calculator() {
        return CALCULATOR;
    }
}
