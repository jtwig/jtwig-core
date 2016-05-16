package org.jtwig.render.expression.test.calculator;

import org.jtwig.exceptions.ResolveValueException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.DefinedTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.value.Undefined;

public class DefinedTestExpressionCalculator implements TestExpressionCalculator<DefinedTestExpression> {
    @Override
    public Object calculate(RenderRequest request, Position position, DefinedTestExpression test, Expression argument) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        try {
            Object value = calculateExpressionService.calculate(request, argument);
            return value != Undefined.UNDEFINED;
        } catch (ResolveValueException e) {
            return false;
        }
    }
}
