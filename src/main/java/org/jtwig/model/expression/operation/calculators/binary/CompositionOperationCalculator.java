package org.jtwig.model.expression.operation.calculators.binary;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.function.Argument;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

import java.util.Collections;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class CompositionOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        Argument argument = new Argument(Optional.<String>absent(), leftOperand);
        FunctionExpression functionExpression;
        if (rightOperand instanceof FunctionExpression) {
            functionExpression = (FunctionExpression) rightOperand;
            functionExpression.getArguments().inject(argument);
        } else if (rightOperand instanceof VariableExpression) {
            String identifier = ((VariableExpression) rightOperand).getIdentifier();
            functionExpression = new FunctionExpression(position, identifier, Collections.singletonList(argument));
        } else {
            throw new CalculationException(errorMessage(position, "Invalid composition expression, expecting a function as second argument"));
        }
        return functionExpression.calculate(context);
    }
}
