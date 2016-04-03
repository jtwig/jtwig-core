package org.jtwig.render.expression.calculator.operation.binary.impl;

import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;

public class AndOperator implements BinaryOperator {
    @Override
    public String symbol() {
        return "and";
    }

    @Override
    public int precedence() {
        return 25;
    }
}
