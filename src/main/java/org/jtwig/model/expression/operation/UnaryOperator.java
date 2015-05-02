package org.jtwig.model.expression.operation;

import org.jtwig.model.expression.operation.calculators.unary.NegativeOperationCalculator;
import org.jtwig.model.expression.operation.calculators.unary.NotOperationCalculator;
import org.jtwig.model.expression.operation.calculators.unary.UnaryOperationCalculator;

public enum UnaryOperator {
//    MATH
    NEGATIVE(new NegativeOperationCalculator()),
//    BOOLEAN
    NOT(new NotOperationCalculator())
    ;

    private final UnaryOperationCalculator calculator;

    UnaryOperator(UnaryOperationCalculator calculator) {
        this.calculator = calculator;
    }

    public UnaryOperationCalculator calculator() {
        return calculator;
    }
}
