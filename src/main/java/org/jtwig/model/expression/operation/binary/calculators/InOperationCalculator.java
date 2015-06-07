package org.jtwig.model.expression.operation.binary.calculators;

import com.google.common.base.Function;
import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;

public class InOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(final RenderContext context, Position position, final Expression leftOperand, Expression rightOperand) {
        return rightOperand.calculate(context).map(new Function<JtwigValue, Object>() {
            @Override
            public Object apply(JtwigValue input) {
                return input.contains(leftOperand.calculate(context));
            }
        });
    }
}
