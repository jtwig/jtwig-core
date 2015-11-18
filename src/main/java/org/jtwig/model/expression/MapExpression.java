package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

import java.util.HashMap;
import java.util.Map;

public class MapExpression extends Expression {
    private final Map<String, Expression> expressions;

    public MapExpression(Position position, Map<String, Expression> expressions) {
        super(position);
        this.expressions = expressions;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        Map<Object, Object> result = new HashMap<>();
        for (Map.Entry<String, Expression> entry : expressions.entrySet()) {
            result.put(entry.getKey(), entry.getValue().calculate(context).asObject());
        }
        return JtwigValueFactory.value(result, context.environment().value());
    }
}
