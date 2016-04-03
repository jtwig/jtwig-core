package org.jtwig.model.expression;

import org.jtwig.model.position.Position;

public class ConstantExpression extends Expression {
    private final Object value;

    public ConstantExpression(Position position, Object value) {
        super(position);
        this.value = value;
    }

    public Object getConstantValue() {
        return value;
    }
}
