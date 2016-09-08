package org.jtwig.render.expression.calculator.operation.binary.impl;

import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;

public class MatchesOperator implements BinaryOperator {
    @Override
    public String symbol() {
        return "matches";
    }

    @Override
    public int precedence() {
        return 25;
    }
}
