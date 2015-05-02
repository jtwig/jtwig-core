package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.model.ByteArrayRenderable;

public class OutputNode extends Node {
    private final Expression expression;

    public OutputNode(Position position, Expression expression) {
        super(position);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Renderable render(RenderContext context) {
        byte[] content = expression
                .calculate(context)
                .asString().getBytes();
        return new ByteArrayRenderable(content);
    }
}
