package org.jtwig.model.expression.operation.calculators.unary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

import java.math.BigDecimal;

public class NegativeOperationCalculator implements UnaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression operand) {
        return new JtwigValue(operand.calculate(context).mandatoryNumber().multiply(new BigDecimal(-1), context.configuration().mathContext()));
    }
}
