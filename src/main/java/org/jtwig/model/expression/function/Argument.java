package org.jtwig.model.expression.function;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.value.JtwigValue;

public class Argument {
    private final Expression expression;

    public Argument(Expression expression) {
        this.expression = expression;
    }

    public JtwigValue calculate (RenderContext context) {
        return expression.calculate(context);
    }
}
