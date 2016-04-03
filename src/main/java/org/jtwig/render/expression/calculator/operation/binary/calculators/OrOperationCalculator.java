package org.jtwig.render.expression.calculator.operation.binary.calculators;

public class OrOperationCalculator implements SimpleBinaryBooleanCalculator {
    @Override
    public boolean calculate(boolean leftBoolean, boolean rightBoolean) {
        return leftBoolean || rightBoolean;
    }
}
