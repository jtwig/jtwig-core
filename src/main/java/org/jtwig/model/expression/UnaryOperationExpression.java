package org.jtwig.model.expression;

import org.jtwig.model.position.Position;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;

public class UnaryOperationExpression extends Expression {
    private final UnaryOperator calculator;
    private final Expression operand;

    public UnaryOperationExpression(Position position, UnaryOperator calculator, Expression operand) {
        super(position);
        this.calculator = calculator;
        this.operand = operand;
    }

    public UnaryOperator getOperator() {
        return calculator;
    }

    public Expression getOperand() {
        return operand;
    }
}
