package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.UnaryOperationExpression;
import org.jtwig.render.RenderRequest;

public class UnaryOperationExpressionCalculator implements ExpressionCalculator<UnaryOperationExpression> {
    @Override
    public Object calculate(RenderRequest request, UnaryOperationExpression expression) {
        return request.getEnvironment().getRenderEnvironment().getUnaryOperationService()
                .calculate(request, expression);
    }
}
