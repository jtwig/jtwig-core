package org.jtwig.model.expression.test;

import org.jtwig.model.expression.Expression;

public class SameAsTestExpression extends TestExpression {
    private final Expression expression;

    public SameAsTestExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
