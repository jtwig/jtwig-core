package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.SameAsTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;

public class SameAsTestExpressionCalculator implements TestExpressionCalculator<SameAsTestExpression> {
    @Override
    public Object calculate(RenderRequest request, Position position, SameAsTestExpression test, Expression argument) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();

        Object argumentValue = calculateExpressionService.calculate(request, argument);
        Object compareValue = calculateExpressionService.calculate(request, test.getExpression());

        return argumentValue == compareValue;
    }
}
