package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IntegerMultiplyOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        BigDecimal leftValue = leftOperand.calculate(context)
                .mandatoryNumber().setScale(0, RoundingMode.HALF_UP);
        BigDecimal rightValue = rightOperand.calculate(context)
                .mandatoryNumber().setScale(0, RoundingMode.HALF_UP);
        return JtwigValueFactory.value(
                leftValue
                        .multiply(rightValue, context.environment().renderConfiguration().mathContext())
                        .setScale(0, RoundingMode.HALF_UP), context.environment().valueConfiguration());
    }
}
