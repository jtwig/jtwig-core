package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.IntegerDivideOperationCalculator;

public class IntDivideOperator implements BinaryOperator {
    private static final IntegerDivideOperationCalculator CALCULATOR = new IntegerDivideOperationCalculator();

    @Override
    public String symbol() {
        return "//";
    }

    @Override
    public int precedence() {
        return 5;
    }

    @Override
    public BinaryOperationCalculator calculator() {
        return CALCULATOR;
    }
}
