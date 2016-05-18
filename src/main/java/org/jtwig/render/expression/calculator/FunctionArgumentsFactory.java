package org.jtwig.render.expression.calculator;

import org.jtwig.functions.ExpressionResolver;
import org.jtwig.functions.FunctionArguments;
import org.jtwig.model.expression.Expression;
import org.jtwig.render.RenderRequest;

import java.util.List;

public class FunctionArgumentsFactory {
    public FunctionArguments create (RenderRequest request, List<Expression> expressions) {
        ExpressionResolver expressionResolver = new ExpressionResolver(request);
        return new FunctionArguments(expressionResolver, expressions);
    }
}
