package org.jtwig.model.expression.test;

import org.jtwig.model.expression.Expression;

public class DivisibleByTestExpression extends TestExpression {
    private final Expression expression;

    public DivisibleByTestExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
