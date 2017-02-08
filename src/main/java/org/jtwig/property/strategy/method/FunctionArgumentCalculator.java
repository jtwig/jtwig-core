package org.jtwig.property.strategy.method;

import org.jtwig.model.expression.Expression;
import org.jtwig.render.RenderRequest;

import java.util.ArrayList;
import java.util.List;

public class FunctionArgumentCalculator {
    public List<Object> calculate (RenderRequest request, List<Expression> arguments) {
        List<Object> result = new ArrayList<>();
        for (Expression argument : arguments) {
            result.add(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument));
        }
        return result;
    }
}
