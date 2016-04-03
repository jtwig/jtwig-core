package org.jtwig.render.expression.calculator.operation.unary.calculators;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

public interface UnaryOperationCalculator {
    Object calculate(RenderRequest request, Position position, Expression operand);
}
