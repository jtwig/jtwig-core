package org.jtwig.model.expression.operation.binary;

import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;

public interface BinaryOperator {
    String symbol ();
    int precedence ();
    BinaryOperationCalculator calculator ();
}
