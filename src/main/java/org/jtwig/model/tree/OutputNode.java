package org.jtwig.model.tree;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;

public class OutputNode extends Node {
    private final Expression expression;

    public OutputNode(Position position, Expression expression) {
        super(position);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
