package org.jtwig.model.expression.test;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.value.JtwigValue;

public abstract class TestExpression {
    public abstract JtwigValue test(RenderContext context, Expression argument);
}
