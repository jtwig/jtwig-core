package org.jtwig.model.expression.operation.binary.calculators;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

public class ModOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        JtwigValue leftValue = leftOperand.calculate(context);
        JtwigValue rightValue = rightOperand.calculate(context);
        return JtwigValueFactory.value(leftValue.mandatoryNumber().remainder(rightValue.mandatoryNumber(), context.environment().renderConfiguration().mathContext()), context.environment().valueConfiguration());
    }
}
