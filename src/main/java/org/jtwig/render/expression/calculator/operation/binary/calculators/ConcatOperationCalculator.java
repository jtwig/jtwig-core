package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

public class ConcatOperationCalculator implements SimpleBinaryOperationCalculator {
    @Override
    public Object calculate(RenderRequest request, Position position, Object left, Object right) {
        return getString(request, left) + getString(request, right);
    }


    private String getString(RenderRequest request, Object input) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(input);
    }
}
