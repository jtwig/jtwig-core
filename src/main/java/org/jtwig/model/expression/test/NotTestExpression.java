package org.jtwig.model.expression.test;

import com.google.common.base.Function;
import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.value.JtwigValue;

public class NotTestExpression extends TestExpression {
    private final boolean invert;
    private final TestExpression testExpression;

    public NotTestExpression(boolean invert, TestExpression testExpression) {
        this.invert = invert;
        this.testExpression = testExpression;
    }

    @Override
    public JtwigValue test(RenderContext context, Expression argument) {
        return testExpression.test(context, argument).map(new Function<JtwigValue, Object>() {
            @Override
            public Object apply(JtwigValue input) {
                if (invert) {
                    return !input.asBoolean();
                } else {
                    return input.asBoolean();
                }
            }
        });
    }
}
