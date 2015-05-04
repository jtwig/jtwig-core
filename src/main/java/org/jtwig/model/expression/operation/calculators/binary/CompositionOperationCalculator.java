package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.InjectableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class CompositionOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        if (rightOperand instanceof InjectableExpression) {
            return ((InjectableExpression) rightOperand).inject(leftOperand).calculate(context);
        } else {
            throw new CalculationException(errorMessage(position, "Invalid composition expression"));
        }
    }
}
