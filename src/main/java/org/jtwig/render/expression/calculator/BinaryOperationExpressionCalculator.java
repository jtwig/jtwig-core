package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.BinaryOperationExpression;
import org.jtwig.render.RenderRequest;

public class BinaryOperationExpressionCalculator implements ExpressionCalculator<BinaryOperationExpression> {
    @Override
    public Object calculate(RenderRequest request, BinaryOperationExpression expression) {
        return request.getEnvironment().getRenderEnvironment().getBinaryOperationService()
                .calculate(request, expression);
    }
}
