package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

public class ConstantExpression extends Expression {
    private final Object value;

    public ConstantExpression(Position position, Object value) {
        super(position);
        this.value = value;
    }

    public Object getConstantValue() {
        return value;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        return JtwigValueFactory.value(value, context.environment().value());
    }
}
