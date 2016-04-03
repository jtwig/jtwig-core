package org.jtwig.render.expression.calculator.operation.unary;

import com.google.common.base.Optional;
import org.jtwig.render.expression.calculator.operation.unary.calculators.UnaryOperationCalculator;

import java.util.Map;

public class UnaryOperationCalculatorSelector {
    private final Map<Class<? extends UnaryOperator>, UnaryOperationCalculator> operationCalculatorMap;

    public UnaryOperationCalculatorSelector(Map<Class<? extends UnaryOperator>, UnaryOperationCalculator> operationCalculatorMap) {
        this.operationCalculatorMap = operationCalculatorMap;
    }

    public Optional<UnaryOperationCalculator> calculatorFor (UnaryOperator operator) {
        return Optional.fromNullable(operationCalculatorMap.get(operator.getClass()));
    }
}
