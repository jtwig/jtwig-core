package org.jtwig.render.expression.calculator.operation.unary.calculators;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.value.convert.Converter;

public class NotOperationCalculator implements UnaryOperationCalculator {
    @Override
    public Object calculate(RenderRequest request, Position position, Expression operand) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        Converter<Boolean> booleanConverter = request.getEnvironment().getValueEnvironment().getBooleanConverter();

        Object operandValue = calculateExpressionService.calculate(request, operand);

        return !booleanConverter.convert(operandValue).or(true);
    }
}
