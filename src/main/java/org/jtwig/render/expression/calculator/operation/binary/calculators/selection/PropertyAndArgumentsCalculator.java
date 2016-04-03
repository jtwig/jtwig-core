package org.jtwig.render.expression.calculator.operation.binary.calculators.selection;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.util.ErrorMessageFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PropertyAndArgumentsCalculator {

    public Response calculate (RenderRequest request, Position position, Expression expression) {
        if (VariableExpression.class.isAssignableFrom(expression.getClass())) {
            return new Response(
                    ((VariableExpression) expression).getIdentifier(),
                    Collections.emptyList()
            );
        } else if (FunctionExpression.class.isAssignableFrom(expression.getClass())) {
            return new Response(
                    ((FunctionExpression) expression).getFunctionIdentifier(),
                    calculateArguments(request, (FunctionExpression) expression)
            );
        } else {
            throw new CalculationException(ErrorMessageFormatter.errorMessage(position, String.format("Expecting variable or function, but got %s", expression.getClass().getSimpleName())));
        }
    }


    private List<Object> calculateArguments(final RenderRequest request, final FunctionExpression expression) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        List<Object> result = new ArrayList<>();
        for (Expression argument : expression.getArguments()) {
            result.add(calculateExpressionService.calculate(request, argument));
        }
        return result;
    }


    public static class Response {
        private final String propertyName;
        private final List<Object> arguments;

        public Response(String propertyName, List<Object> arguments) {
            this.propertyName = propertyName;
            this.arguments = arguments;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public List<Object> getArguments() {
            return arguments;
        }
    }
}
