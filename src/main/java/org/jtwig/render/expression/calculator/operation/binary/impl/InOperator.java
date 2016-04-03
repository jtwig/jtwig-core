package org.jtwig.render.expression.calculator.operation.binary.impl;

import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;

public class InOperator implements BinaryOperator {
    @Override
    public String symbol() {
        return "in";
    }

    @Override
    public int precedence() {
        return 15;
    }
}
