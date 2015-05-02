package org.jtwig.model.expression.operation;

import org.jtwig.model.expression.operation.calculators.binary.*;

public enum BinaryOperator {
    // Jtwig
    SELECTION(1, new SelectionOperationCalculator()),
    COMPOSITION(200, new CompositionOperationCalculator()),
    CONCAT(2, new ConcatOperationCalculator()),

    // MATH
    SUM(20, new SumOperationCalculator()),
    SUBTRACT(20, new SubtractOperationCalculator()),
    INT_DIVIDE(30, new IntegerDivideOperationCalculator()),
    INT_MULTIPLY(30, new IntegerMultiplyOperationCalculator()),
    MULTIPLY(30, new MultiplyOperationCalculator()),
    DIVIDE(30, new DivideOperationCalculator()),
    MOD(30, new ModOperationCalculator()),

    // CONDITIONAL
    LESS(10, new LessOperationCalculator()),
    LESS_OR_EQUAL(10, new LessOrEqualOperationCalculator()),
    GREATER(10, new GreaterOperationCalculator()),
    GREATER_OR_EQUAL(10, new GreaterOrEqualOperationCalculator()),

    // BOOLEAN
    AND(10, new AndOperationCalculator()),
    OR(10, new OrOperationCalculator()),
    EQUIVALENT(5, new EquivalentOperationCalculator()),
    DIFFERENT(5, new DifferentOperationCalculator());

    private final Integer precedence;
    private final BinaryOperationCalculator calculator;

    BinaryOperator(int precedence, BinaryOperationCalculator calculator) {
        this.precedence = precedence;
        this.calculator = calculator;
    }

    public Integer precedence() {
        return precedence;
    }

    public BinaryOperationCalculator calculator() {
        return calculator;
    }

}
