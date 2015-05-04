package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

public class MultiplyOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        JtwigValue leftValue = leftOperand.calculate(context);
        JtwigValue rightValue = rightOperand.calculate(context);
        return new JtwigValue(leftValue.asNumber().multiply(rightValue.asNumber(), context.configuration().mathContext()));
    }
}
