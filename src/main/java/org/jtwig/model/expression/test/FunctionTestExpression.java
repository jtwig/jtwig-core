package org.jtwig.model.expression.test;

import org.jtwig.model.expression.InjectableExpression;

public class FunctionTestExpression extends TestExpression {
    private final InjectableExpression injectableExpression;

    public FunctionTestExpression(InjectableExpression injectableExpression) {
        this.injectableExpression = injectableExpression;
    }

    public InjectableExpression getInjectableExpression() {
        return injectableExpression;
    }
}
