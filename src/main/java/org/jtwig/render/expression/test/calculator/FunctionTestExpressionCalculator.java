package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.FunctionTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;

public class FunctionTestExpressionCalculator implements TestExpressionCalculator<FunctionTestExpression> {
    @Override
    public Object calculate(RenderRequest request, Position position, FunctionTestExpression test, Expression argument) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        Expression expression = test.getInjectableExpression().inject(argument);
        return calculateExpressionService.calculate(request, expression);
    }
}
