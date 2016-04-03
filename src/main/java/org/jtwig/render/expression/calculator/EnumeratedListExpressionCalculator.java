package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.EnumeratedListExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;

import java.util.ArrayList;
import java.util.Collection;

public class EnumeratedListExpressionCalculator implements ExpressionCalculator<EnumeratedListExpression> {
    @Override
    public Object calculate(RenderRequest request, EnumeratedListExpression expression) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();

        Collection<Object> resolved = new ArrayList<>();
        for (Expression element : expression.getExpressions()) {
            resolved.add(calculateExpressionService.calculate(request, element));
        }

        return resolved;
    }
}
