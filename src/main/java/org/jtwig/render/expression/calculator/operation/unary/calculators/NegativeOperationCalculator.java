package org.jtwig.render.expression.calculator.operation.unary.calculators;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.value.convert.Converter;

import java.math.BigDecimal;

public class NegativeOperationCalculator implements UnaryOperationCalculator {
    @Override
    public Object calculate(RenderRequest request, Position position, Expression operand) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        Converter<BigDecimal> numberConverter = request.getEnvironment().getValueEnvironment().getNumberConverter();

        Object calculate = calculateExpressionService.calculate(request, operand);
        BigDecimal value = numberConverter.convert(calculate).orThrow(position, String.format("Unable to convert '%s' to a number", calculate));
        return value.multiply(new BigDecimal("-1"));
    }

}
