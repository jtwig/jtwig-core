package org.jtwig.model.expression.operation.unary.calculators;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

import java.math.BigDecimal;

public class NegativeOperationCalculator implements UnaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression operand) {
        return JtwigValueFactory.value(operand.calculate(context).mandatoryNumber().multiply(new BigDecimal(-1), context.environment()
                .valueConfiguration().getMathContext()), context.environment().valueConfiguration());
    }
}
