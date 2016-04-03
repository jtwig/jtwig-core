package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

public class DifferentOperationCalculator implements SimpleBinaryOperationCalculator {
    @Override
    public Object calculate(RenderRequest request, Position position, Object left, Object right) {
        return request.getEnvironment().getValueEnvironment().getValueComparator().compare(request, left, right) != 0;
    }
}
