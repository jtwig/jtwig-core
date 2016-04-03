package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.DivisibleByTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.value.convert.Converter;

import java.math.BigDecimal;
import java.math.MathContext;

public class DivisibleByTestExpressionCalculator implements TestExpressionCalculator<DivisibleByTestExpression> {
    @Override
    public Object calculate(RenderRequest request, Position position, DivisibleByTestExpression test, Expression argument) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        Converter<BigDecimal> numberConverter = request.getEnvironment().getValueEnvironment().getNumberConverter();

        MathContext mathContext = request.getEnvironment().getValueEnvironment().getMathContext();
        BigDecimal testValue = getNumber(request, calculateExpressionService, numberConverter, argument);
        BigDecimal divisor = getNumber(request, calculateExpressionService, numberConverter, test.getExpression());

        return testValue.remainder(divisor, mathContext).equals(BigDecimal.ZERO);
    }

    private BigDecimal getNumber(RenderRequest request, CalculateExpressionService calculateExpressionService, Converter<BigDecimal> numberConverter, Expression expression) {
        Object calculate = calculateExpressionService.calculate(request, expression);
        return numberConverter.convert(calculate).orThrow(expression.getPosition(), String.format("Cannot convert '%s' to number", calculate));
    }
}
