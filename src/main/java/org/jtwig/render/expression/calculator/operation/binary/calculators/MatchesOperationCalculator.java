package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

public class MatchesOperationCalculator implements SimpleBinaryOperationCalculator {
    @Override
    public Object calculate(RenderRequest request, Position position, Object left, Object right) {
        String leftOperand = request.getEnvironment().getValueEnvironment().getStringConverter().convert(left);
        String rightOperand = request.getEnvironment().getValueEnvironment().getStringConverter().convert(right);

        return leftOperand.matches(rightOperand);
    }
}
