package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.render.expression.CalculateExpressionService;

public class SimpleOperationCalculator implements BinaryOperationCalculator {
    private final SimpleBinaryOperationCalculator simpleBinaryOperationCalculator;

    public SimpleOperationCalculator(SimpleBinaryOperationCalculator simpleBinaryOperationCalculator) {
        this.simpleBinaryOperationCalculator = simpleBinaryOperationCalculator;
    }

    @Override
    public Object calculate(Request request) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();

        Object leftValue = calculateExpressionService.calculate(request, request.getLeftOperand());
        Object rightValue = calculateExpressionService.calculate(request, request.getRightOperand());

        return simpleBinaryOperationCalculator.calculate(request, request.getPosition(), leftValue, rightValue);
    }
}
