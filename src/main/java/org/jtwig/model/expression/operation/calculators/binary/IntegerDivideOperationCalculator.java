package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.configuration.MathContextParameter;
import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IntegerDivideOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        BigDecimal leftValue = leftOperand.calculate(context)
                .asNumber().setScale(0, RoundingMode.HALF_UP);
        BigDecimal rightValue = rightOperand.calculate(context)
                .asNumber().setScale(0, RoundingMode.HALF_UP);
        return new JtwigValue(
                leftValue
                        .divide(rightValue, context.configuration().parameter(MathContextParameter.mathContext()))
                        .setScale(0, RoundingMode.HALF_UP)
        );
    }
}
