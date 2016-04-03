package org.jtwig.model.tree;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;

import java.util.Collection;

public class ExtendsNode extends Node {
    private final Expression extendsExpression;
    private final Collection<Node> nodes;

    public ExtendsNode(Position position, Expression extendsExpression, Collection<Node> nodes) {
        super(position);
        this.extendsExpression = extendsExpression;
        this.nodes = nodes;
    }

    public Expression getExtendsExpression() {
        return extendsExpression;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }
}
