package org.jtwig.render.expression.calculator.operation.unary;

public interface UnaryOperator {
    int precedence ();
    String symbol ();
}
