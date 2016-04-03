package org.jtwig.render.expression.calculator;

import com.google.common.base.Optional;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.ComprehensionListExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;
import org.jtwig.util.ErrorMessageFormatter;

import java.util.List;

public class ComprehensionListExpressionCalculator implements ExpressionCalculator<ComprehensionListExpression> {
    @Override
    public Object calculate(RenderRequest request, ComprehensionListExpression expression) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        EnumerationListStrategy listEnumerationStrategy = request.getEnvironment().getListEnumerationStrategy();


        Object startValue = calculateExpressionService.calculate(request, expression.getStart());
        Object endValue = calculateExpressionService.calculate(request, expression.getEnd());

        Optional<List<Object>> result = listEnumerationStrategy.enumerate(request, startValue, endValue);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new CalculationException(ErrorMessageFormatter.errorMessage(expression.getPosition(), String.format("Unable to calculate a list from a comprehension list starting with '%s' and ending with '%s'", startValue, endValue)));
        }
    }
}
