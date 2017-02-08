package org.jtwig.property.selection;

import org.jtwig.environment.Environment;
import org.jtwig.model.expression.Expression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.RenderContext;

public class SelectionRequest extends RenderRequest {
    private final Expression leftExpression;
    private final Expression rightExpression;

    public SelectionRequest(RenderContext renderContext, Environment environment, Expression leftExpression, Expression rightExpression) {
        super(renderContext, environment);
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }
}
