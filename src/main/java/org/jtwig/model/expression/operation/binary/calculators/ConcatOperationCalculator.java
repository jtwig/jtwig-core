package org.jtwig.model.expression.operation.binary.calculators;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

public class ConcatOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        return JtwigValueFactory.value(new StringBuilder()
                .append(leftOperand.calculate(context).asString())
                .append(rightOperand.calculate(context).asString())
                .toString(), context.environment().valueConfiguration());
    }
}
