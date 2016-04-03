package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.InjectableExpression;
import org.jtwig.render.expression.CalculateExpressionService;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class CompositionOperationCalculator implements BinaryOperationCalculator {
    @Override
    public Object calculate(Request request) {
        if (request.getRightOperand() instanceof InjectableExpression) {
            Expression expression = ((InjectableExpression) request.getRightOperand()).inject(request.getLeftOperand());
            CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
            return calculateExpressionService.calculate(request, expression);
        } else {
            throw new CalculationException(errorMessage(request.getPosition(), "Invalid composition expression"));
        }
    }
}
