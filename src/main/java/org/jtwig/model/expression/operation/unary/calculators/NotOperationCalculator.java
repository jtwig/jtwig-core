package org.jtwig.model.expression.operation.unary.calculators;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

public class NotOperationCalculator implements UnaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression operand) {
        return JtwigValueFactory.value(!operand.calculate(context).asBoolean(), context.environment().valueConfiguration());
    }
}
