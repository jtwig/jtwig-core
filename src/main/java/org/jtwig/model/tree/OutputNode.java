package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.StringRenderable;
import org.jtwig.util.JtwigValue;

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
        JtwigValue jtwigValue = expression.calculate(context);
        String string = jtwigValue.asString();
        EscapeMode mode = context.currentNode().mode().or(context.escapeMode());
        return new StringRenderable(string, mode);
    }
}
