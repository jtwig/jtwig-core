package org.jtwig.render.expression.calculator.operation.binary.calculators;

public class AndOperationCalculator implements SimpleBinaryBooleanCalculator {
    @Override
    public boolean calculate(boolean leftBoolean, boolean rightBoolean) {
        return leftBoolean && rightBoolean;
    }
}
