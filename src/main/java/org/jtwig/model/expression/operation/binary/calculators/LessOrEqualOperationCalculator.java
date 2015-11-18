package org.jtwig.model.expression.operation.binary.calculators;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

public class LessOrEqualOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        JtwigValue left = leftOperand.calculate(context);
        JtwigValue right = rightOperand.calculate(context);
        return JtwigValueFactory.value(left.isLowerThan(right) || left.isEqualTo(right), context.environment().value());
    }
}
