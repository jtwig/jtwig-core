package org.jtwig.functions;

import com.google.common.base.Function;
import org.jtwig.model.expression.Expression;

public class EmptyExpressionResolver implements Function<Expression, Object> {
    public static EmptyExpressionResolver INSTANCE = new EmptyExpressionResolver();

    public static EmptyExpressionResolver instance () {
        return INSTANCE;
    }

    private EmptyExpressionResolver() {}

    @Override
    public Object apply(Expression input) {
        return null;
    }
}
