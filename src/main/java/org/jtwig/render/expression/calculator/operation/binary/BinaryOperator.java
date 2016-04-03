package org.jtwig.render.expression.calculator.operation.binary;

public interface BinaryOperator {
    String symbol ();
    int precedence ();
}
