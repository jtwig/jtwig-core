package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.operation.unary.calculators.UnaryOperationCalculator;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;

public class UnaryOperationExpression extends Expression {
    private final UnaryOperationCalculator calculator;
    private final Expression operand;

    public UnaryOperationExpression(Position position, UnaryOperationCalculator calculator, Expression operand) {
        super(position);
        this.calculator = calculator;
        this.operand = operand;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        return calculator.calculate(context, getPosition(), operand);
    }
}
