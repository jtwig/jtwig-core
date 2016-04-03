package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.compare.ValueComparator;

public class GreaterOrEqualOperationCalculator implements SimpleBinaryOperationCalculator {
    @Override
    public Object calculate(RenderRequest request, Position position, Object left, Object right) {
        ValueComparator valueComparator = request.getEnvironment().getValueEnvironment().getValueComparator();
        return valueComparator.compare(request, left, right) >= 0;
    }
}
