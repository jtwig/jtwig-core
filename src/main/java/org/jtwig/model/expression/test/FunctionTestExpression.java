package org.jtwig.model.expression.test;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.InjectableExpression;
import org.jtwig.value.JtwigValue;

public class FunctionTestExpression extends TestExpression {
    private final InjectableExpression injectableExpression;

    public FunctionTestExpression(InjectableExpression injectableExpression) {
        this.injectableExpression = injectableExpression;
    }

    @Override
    public JtwigValue test(RenderContext context, Expression argument) {
        return injectableExpression.inject(argument).calculate(context);
    }
}
