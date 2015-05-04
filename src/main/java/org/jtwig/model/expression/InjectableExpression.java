package org.jtwig.model.expression;

import org.jtwig.model.position.Position;

public abstract class InjectableExpression extends Expression {
    protected InjectableExpression(Position position) {
        super(position);
    }

    public abstract Expression inject (Expression expression);
}
