package org.jtwig.render.expression.calculator.operation.unary.impl;

import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;

public class NegativeUnaryOperator implements UnaryOperator {
    @Override
    public String symbol() {
        return "-";
    }

    @Override
    public int precedence() {
        return 5;
    }
}
