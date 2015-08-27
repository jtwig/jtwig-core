package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

import java.util.ArrayList;
import java.util.Collection;

public class EnumeratedListExpression extends Expression {
    private final Collection<Expression> expressions;

    public EnumeratedListExpression(Position position, Collection<Expression> expressions) {
        super(position);
        this.expressions = expressions;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        Collection<Object> resolved = new ArrayList<>();
        for (Expression expression : expressions) {
            resolved.add(expression.calculate(context).asObject());
        }
        return JtwigValueFactory.value(resolved, context.environment().valueConfiguration());
    }
}
