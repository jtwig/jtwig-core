package org.jtwig.model.expression;

import org.jtwig.model.position.Position;

public class TernaryOperationExpression extends Expression {
    private final Expression firstExpression;
    private final Expression secondExpression;
    private final Expression thirdExpression;

    public TernaryOperationExpression(Position position, Expression firstExpression, Expression secondExpression, Expression thirdExpression) {
        super(position);
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.thirdExpression = thirdExpression;
    }

    public Expression getFirstExpression() {
        return firstExpression;
    }

    public Expression getSecondExpression() {
        return secondExpression;
    }

    public Expression getThirdExpression() {
        return thirdExpression;
    }
}
