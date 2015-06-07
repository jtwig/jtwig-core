package org.jtwig.model.expression.operation.unary;

import org.jtwig.model.expression.operation.unary.calculators.UnaryOperationCalculator;

public interface UnaryOperator {
    int precedence ();
    String symbol ();
    UnaryOperationCalculator calculator ();
}
