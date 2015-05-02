package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

public class LessOrEqualOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        JtwigValue left = leftOperand.calculate(context);
        JtwigValue right = rightOperand.calculate(context);
        return new JtwigValue(left.compareTo(right) <= 0);
    }
}
