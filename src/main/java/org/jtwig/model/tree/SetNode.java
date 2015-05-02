package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.model.EmptyRenderable;

public class SetNode extends Node {
    private final VariableExpression variableExpression;
    private final Expression expression;

    public SetNode(Position position, VariableExpression variableExpression, Expression expression) {
        super(position);
        this.variableExpression = variableExpression;
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Renderable render(RenderContext context) {
        Object value = expression.calculate(context).asObject();
        context.valueContext().add(variableExpression.getIdentifier(), value);
        return EmptyRenderable.instance();
    }
}
