package org.jtwig.render.expression.calculator.operation.binary.impl;

import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;

public class SelectionOperator implements BinaryOperator {
    public static final String OPERATOR = ".";

    @Override
    public String symbol() {
        return OPERATOR;
    }

    @Override
    public int precedence() {
        return 1;
    }
}
