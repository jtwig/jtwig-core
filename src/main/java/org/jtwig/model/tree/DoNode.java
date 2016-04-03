package org.jtwig.model.tree;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;

public class DoNode extends Node {
    private final Expression expression;

    public DoNode(Position position, Expression expression) {
        super(position);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
