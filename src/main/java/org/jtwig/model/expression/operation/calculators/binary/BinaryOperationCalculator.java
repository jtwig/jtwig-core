package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;

public interface BinaryOperationCalculator {
    JtwigValue calculate (RenderContext context, Position position, Expression leftOperand, Expression rightOperand);
}
