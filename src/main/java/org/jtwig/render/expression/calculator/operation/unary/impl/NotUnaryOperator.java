package org.jtwig.render.expression.calculator.operation.unary.impl;

import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;

public class NotUnaryOperator implements UnaryOperator {
    @Override
    public String symbol() {
        return "not";
    }

    @Override
    public int precedence() {
        return 10;
    }
}
