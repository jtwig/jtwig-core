package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.EmptyRenderable;

public class DoNode extends Node {
    private final Expression expression;

    public DoNode(Position position, Expression expression) {
        super(position);
        this.expression = expression;
    }

    @Override
    public Renderable render(RenderContext context) {
        expression.calculate(context);
        return EmptyRenderable.instance();
    }
}
