package org.jtwig.render.expression.calculator.operation.binary.calculators.selection;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.property.selection.SelectionPropertyResolver;
import org.jtwig.property.selection.SelectionRequest;
import org.jtwig.property.selection.SelectionResult;
import org.jtwig.render.expression.calculator.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.value.Undefined;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class SelectionOperationCalculator implements BinaryOperationCalculator {
    private final SelectionErrorMessageGenerator selectionErrorMessageGenerator;

    public SelectionOperationCalculator(SelectionErrorMessageGenerator selectionErrorMessageGenerator) {
        this.selectionErrorMessageGenerator = selectionErrorMessageGenerator;
    }

    @Override
    public Object calculate(Request request) {
        SelectionPropertyResolver selectionPropertyResolver = request.getEnvironment().getPropertyResolverEnvironment().getSelectionPropertyResolver();
        SelectionResult result = selectionPropertyResolver.resolve(new SelectionRequest(
                request.getRenderContext(), request.getEnvironment(),
                request.getLeftOperand(),
                request.getRightOperand()
        ));


        if (result.getResolvedValue().isPresent()) {
            return result.getResolvedValue().get().getValue();
        } else {
            if (request.getEnvironment().getRenderEnvironment().getStrictMode()) {
                throw new CalculationException(errorMessage(request.getPosition(), selectionErrorMessageGenerator.explain(request.getLeftOperand(), request.getRightOperand())));
            } else {
                return Undefined.UNDEFINED;
            }
        }
    }
}
