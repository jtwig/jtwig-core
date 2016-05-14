package org.jtwig.render.expression.calculator.operation.binary.impl;

import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;

public class ConcatOperator implements BinaryOperator {
    @Override
    public String symbol() {
        return "~";
    }

    @Override
    public int precedence() {
        return 12;
    }
}
