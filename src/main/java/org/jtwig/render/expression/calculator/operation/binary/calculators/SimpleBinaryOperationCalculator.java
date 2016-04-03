package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

public interface SimpleBinaryOperationCalculator {
    Object calculate(RenderRequest request, Position position, Object left, Object right);
}
