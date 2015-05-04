package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

import java.math.BigDecimal;

public class DivideOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        BigDecimal leftValue = leftOperand.calculate(context).mandatoryNumber();
        BigDecimal rightValue = rightOperand.calculate(context).mandatoryNumber();
        BigDecimal value = leftValue
                .divide(rightValue, context.configuration()
                        .mathContext());
        return new JtwigValue(value);
    }
}
