package org.jtwig.render.expression.calculator.operation.unary;

import com.google.common.base.Optional;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.UnaryOperationExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.calculator.operation.unary.calculators.UnaryOperationCalculator;
import org.jtwig.util.ErrorMessageFormatter;

public class UnaryOperationService {
    private final UnaryOperationCalculatorSelector unaryOperationCalculatorSelector;

    public UnaryOperationService(UnaryOperationCalculatorSelector unaryOperationCalculatorSelector) {
        this.unaryOperationCalculatorSelector = unaryOperationCalculatorSelector;
    }

    public Object calculate (RenderRequest request, UnaryOperationExpression expression) {
        Optional<UnaryOperationCalculator> optional = unaryOperationCalculatorSelector.calculatorFor(expression.getOperator());
        if (optional.isPresent()) {
            return optional.get().calculate(request, expression.getPosition(), expression.getOperand());
        } else {
            throw new CalculationException(ErrorMessageFormatter.errorMessage(expression.getPosition(), String.format("No calculator for operator %s found", expression.getOperator().getClass())));
        }
    }
}
