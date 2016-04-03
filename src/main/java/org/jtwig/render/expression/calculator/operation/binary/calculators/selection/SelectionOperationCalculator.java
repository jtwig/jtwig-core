package org.jtwig.render.expression.calculator.operation.binary.calculators.selection;

import com.google.common.base.Optional;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.expression.calculator.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.value.Undefined;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class SelectionOperationCalculator implements BinaryOperationCalculator {
    private final PropertyAndArgumentsCalculator propertyAndArgumentsCalculator;
    private final ResolveSelectionPropertyCalculator resolveSelectionPropertyCalculator;

    public SelectionOperationCalculator(PropertyAndArgumentsCalculator propertyAndArgumentsCalculator, ResolveSelectionPropertyCalculator resolveSelectionPropertyCalculator) {
        this.propertyAndArgumentsCalculator = propertyAndArgumentsCalculator;
        this.resolveSelectionPropertyCalculator = resolveSelectionPropertyCalculator;
    }

    @Override
    public Object calculate(Request request) {
        Object leftValue = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, request.getLeftOperand());
        PropertyAndArgumentsCalculator.Response response = propertyAndArgumentsCalculator.calculate(request, request.getPosition(), request.getRightOperand());
        Optional<Value> optional = resolveSelectionPropertyCalculator.calculate(request, request.getPosition(), response.getPropertyName(), response.getArguments(), leftValue);

        if (optional.isPresent()) {
            return optional.get().getValue();
        } else {
            if (request.getEnvironment().getRenderEnvironment().getStrictMode()) {
                throw new CalculationException(errorMessage(request.getPosition(), String.format("Impossible to access an attribute '%s' on '%s'", response.getPropertyName(), leftValue)));
            } else {
                return Undefined.UNDEFINED;
            }
        }
    }
}
