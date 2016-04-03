package org.jtwig.model.expression;

import org.jtwig.model.position.Position;

import java.util.Collection;

public class EnumeratedListExpression extends Expression {
    private final Collection<Expression> expressions;

    public EnumeratedListExpression(Position position, Collection<Expression> expressions) {
        super(position);
        this.expressions = expressions;
    }

    public Collection<Expression> getExpressions() {
        return expressions;
    }
}
