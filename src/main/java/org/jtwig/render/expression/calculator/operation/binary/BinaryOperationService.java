package org.jtwig.render.expression.calculator.operation.binary;

import com.google.common.base.Optional;
import org.jtwig.model.expression.BinaryOperationExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.calculator.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.util.ErrorMessageFormatter;

public class BinaryOperationService {
    private final BinaryOperationCalculatorSelector binaryOperationCalculatorSelector;

    public BinaryOperationService(BinaryOperationCalculatorSelector binaryOperationCalculatorSelector) {
        this.binaryOperationCalculatorSelector = binaryOperationCalculatorSelector;
    }

    public Object calculate(RenderRequest request, BinaryOperationExpression expression) {
        BinaryOperator binaryOperator = expression.getBinaryOperator();
        Optional<BinaryOperationCalculator> optional = binaryOperationCalculatorSelector.calculatorFor(binaryOperator);
        if (optional.isPresent()) {
            return optional.get().calculate(new BinaryOperationCalculator.Request(request, expression.getPosition(), expression.getLeftOperand(), expression.getRightOperand()));
        } else {
            throw new IllegalArgumentException(ErrorMessageFormatter.errorMessage(expression.getPosition(), String.format("No calculator implementation for operation '%s'", binaryOperator.getClass())));
        }
    }
}
