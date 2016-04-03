package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.TernaryOperationExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.value.convert.Converter;

public class TernaryExpressionCalculator implements ExpressionCalculator<TernaryOperationExpression> {
    @Override
    public Object calculate(RenderRequest request, TernaryOperationExpression expression) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        Converter<Boolean> booleanConverter = request.getEnvironment().getValueEnvironment().getBooleanConverter();

        Object calculate = calculateExpressionService.calculate(request, expression.getFirstExpression());
        if (booleanConverter.convert(calculate).or(true)) {
            return calculateExpressionService.calculate(request, expression.getSecondExpression());
        } else {
            return calculateExpressionService.calculate(request, expression.getThirdExpression());
        }
    }
}
