package org.jtwig.model.expression;

import org.jtwig.model.position.Position;

import java.util.Map;

public class MapExpression extends Expression {
    private final Map<String, Expression> expressions;

    public MapExpression(Position position, Map<String, Expression> expressions) {
        super(position);
        this.expressions = expressions;
    }

    public Map<String, Expression> getExpressions() {
        return expressions;
    }
}
