package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.TestOperationExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.test.CalculateTestExpressionService;

public class TestOperationExpressionCalculator implements ExpressionCalculator<TestOperationExpression> {
    @Override
    public Object calculate(RenderRequest request, TestOperationExpression expression) {
        CalculateTestExpressionService calculateTestExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateTestExpressionService();

        return calculateTestExpressionService.calculate(request, expression.getPosition(), expression.getTestExpression(), expression.getArgument());
    }
}
