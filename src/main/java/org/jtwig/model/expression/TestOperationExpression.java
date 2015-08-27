package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;

public class TestOperationExpression extends Expression {
    private final Expression argument;
    private final TestExpression testExpression;

    public TestOperationExpression(Position position, Expression argument, TestExpression testExpression) {
        super(position);
        this.argument = argument;
        this.testExpression = testExpression;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        return testExpression.test(context, argument);
    }
}
