package org.jtwig.render.expression.calculator.operation.binary.impl;

import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;

public class OrOperator implements BinaryOperator {
    @Override
    public String symbol() {
        return "or";
    }

    @Override
    public int precedence() {
        return 25;
    }
}
