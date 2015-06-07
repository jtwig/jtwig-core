package org.jtwig.model.expression.operation.binary.calculators;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

public class AndOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        return JtwigValueFactory.value(leftOperand.calculate(context).asBoolean() && rightOperand.calculate(context).asBoolean(), context.environment().valueConfiguration());
    }
}
