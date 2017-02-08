package org.jtwig.property.resolver.request;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.render.RenderRequest;

import java.util.ArrayList;
import java.util.List;

public class ArgumentsExtractor {
    public List<Object> extract (RenderRequest request, Expression expression) {
        ArrayList<Object> arguments = new ArrayList<>();
        if (expression instanceof FunctionExpression) {
            for (Expression argument : ((FunctionExpression) expression).getArguments()) {
                Object argumentValue = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument);
                arguments.add(argumentValue);
            }
        }
        return arguments;
    }
}
