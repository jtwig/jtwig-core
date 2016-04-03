package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;

public class BooleanOperationCalculator implements SimpleBinaryOperationCalculator {
    private final SimpleBinaryBooleanCalculator binaryBooleanCalculator;

    public BooleanOperationCalculator(SimpleBinaryBooleanCalculator binaryBooleanCalculator) {
        this.binaryBooleanCalculator = binaryBooleanCalculator;
    }
    @Override
    public Object calculate(RenderRequest request, Position position, Object left, Object right) {
        Converter<Boolean> booleanConverter = request.getEnvironment().getValueEnvironment().getBooleanConverter();

        boolean leftValue = booleanConverter.convert(left).or(true);
        boolean rightValue = booleanConverter.convert(right).or(true);

        return binaryBooleanCalculator.calculate(leftValue, rightValue);
    }
}
