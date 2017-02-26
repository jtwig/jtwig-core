package org.jtwig.render.expression.calculator;

import org.jtwig.exceptions.ResolveValueException;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.value.Undefined;
import org.jtwig.value.context.ValueContext;

public class VariableExpressionCalculator implements ExpressionCalculator<VariableExpression> {
    @Override
    public Object calculate(RenderRequest request, VariableExpression expression) {
        String identifier = expression.getIdentifier();
        Object value = request.getRenderContext().getCurrent(ValueContext.class).resolve(identifier);

        if (value == Undefined.UNDEFINED && request.getEnvironment().getRenderEnvironment().getStrictMode()) {
            throw new ResolveValueException(ErrorMessageFormatter.errorMessage(expression.getPosition(), String.format("Variable '%s' undefined", identifier)));
        } else {
            return value;
        }
    }
}
