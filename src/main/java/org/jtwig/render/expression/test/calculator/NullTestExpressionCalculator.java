package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.NullTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.value.Undefined;

public class NullTestExpressionCalculator implements TestExpressionCalculator<NullTestExpression> {
    @Override
    public Object calculate(RenderRequest request, Position position, NullTestExpression test, Expression argument) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        Object argumentValue = calculateExpressionService.calculate(request, argument);

        if (request.getEnvironment().getRenderEnvironment().getStrictMode()) {
            return argumentValue == null;
        } else {
            return argumentValue == null || argumentValue == Undefined.UNDEFINED;
        }
    }
}
