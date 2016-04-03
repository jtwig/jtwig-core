package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.NotTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.test.CalculateTestExpressionService;
import org.jtwig.value.convert.Converter;

public class NotTestExpressionCalculator implements TestExpressionCalculator<NotTestExpression> {
    @Override
    public Object calculate(RenderRequest request, Position position, NotTestExpression test, Expression argument) {
        CalculateTestExpressionService calculateTestExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateTestExpressionService();
        Converter<Boolean> booleanConverter = request.getEnvironment().getValueEnvironment().getBooleanConverter();

        Object calculate = calculateTestExpressionService.calculate(request, position, test.getTestExpression(), argument);

        return !booleanConverter.convert(calculate).or(true);
    }
}
