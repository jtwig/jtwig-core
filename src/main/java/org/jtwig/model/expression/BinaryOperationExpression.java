package org.jtwig.model.expression;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.position.Position;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class BinaryOperationExpression extends InjectableExpression {
    private final Expression leftOperand;
    private final BinaryOperator binaryOperator;
    private final Expression rightOperand;

    public BinaryOperationExpression(Position position, Expression leftOperand, BinaryOperator binaryOperator, Expression rightOperand) {
        super(position);
        this.leftOperand = leftOperand;
        this.binaryOperator = binaryOperator;
        this.rightOperand = rightOperand;
    }

    public Expression getLeftOperand() {
        return leftOperand;
    }

    public BinaryOperator getBinaryOperator() {
        return binaryOperator;
    }

    public Expression getRightOperand() {
        return rightOperand;
    }

    @Override
    public Expression inject(Expression expression) {
        if (leftOperand instanceof InjectableExpression) {
            Expression inject = ((InjectableExpression) leftOperand).inject(expression);
            return new BinaryOperationExpression(getPosition(), inject, binaryOperator, rightOperand);
        } else {
            throw new CalculationException(errorMessage(getPosition(), "Invalid expression, expecting a valid injectable expression (binary operator, variable or function)"));
        }
    }
}
