package org.jtwig.model.expression.test;

import com.google.common.base.Function;
import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.value.JtwigValue;

public class SameAsTestExpression extends TestExpression {
    private final Expression expression;

    public SameAsTestExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public JtwigValue test(final RenderContext context, Expression argument) {
        return argument.calculate(context).map(new Function<JtwigValue, Object>() {
            @Override
            public Object apply(JtwigValue input) {
                return input.asObject() == expression.calculate(context).asObject();
            }
        });
    }
}
