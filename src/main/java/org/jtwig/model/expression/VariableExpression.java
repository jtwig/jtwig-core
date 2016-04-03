package org.jtwig.model.expression;

import org.jtwig.model.position.Position;

import java.util.Collections;

public class VariableExpression extends InjectableExpression {
    private final String identifier;

    public VariableExpression(Position position, String identifier) {
        super(position);
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Expression inject(Expression expression) {
        return new FunctionExpression(getPosition(), identifier, Collections.singletonList(expression));
    }
}
