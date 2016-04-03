package org.jtwig.render.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.render.RenderRequest;

public class CalculateExpressionService {
    private final ExpressionCalculatorSelector calculatorSelector;

    public CalculateExpressionService(ExpressionCalculatorSelector calculatorSelector) {
        this.calculatorSelector = calculatorSelector;
    }

    public Object calculate (RenderRequest request, Expression expression) {
        return calculatorSelector.calculatorFor(expression)
                .calculate(request, expression);
    }
}
