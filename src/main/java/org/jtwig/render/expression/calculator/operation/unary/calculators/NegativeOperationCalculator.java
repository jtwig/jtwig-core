package org.jtwig.render.expression.calculator.operation.unary.calculators;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.value.convert.Converter;

import java.math.BigDecimal;

public class NegativeOperationCalculator implements UnaryOperationCalculator {
    @Override
    public Object calculate(RenderRequest request, Position position, Expression operand) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        Converter<BigDecimal> numberConverter = request.getEnvironment().getValueEnvironment().getNumberConverter();

        Object calculate = calculateExpressionService.calculate(request, operand);
        Converter.Result<BigDecimal> decimalResult = numberConverter.convert(calculate);

        if (!decimalResult.isDefined()) {
            throw new CalculationException(ErrorMessageFormatter.errorMessage(position, String.format("Unable to convert '%s' to a number", calculate)));
        }
        return decimalResult.get().multiply(new BigDecimal("-1"));
    }

}
