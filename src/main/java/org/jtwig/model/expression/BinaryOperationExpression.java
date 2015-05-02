package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.operation.BinaryOperator;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

public class BinaryOperationExpression extends Expression {
    private final Expression leftOperand;
    private final BinaryOperator operator;
    private final Expression rightOperand;

    public BinaryOperationExpression(Position position, Expression leftOperand, BinaryOperator operator, Expression rightOperand) {
        super(position);
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    public Expression getLeftOperand() {
        return leftOperand;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public Expression getRightOperand() {
        return rightOperand;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        return operator.calculator().calculate(context, getPosition(), leftOperand, rightOperand);
    }
}
