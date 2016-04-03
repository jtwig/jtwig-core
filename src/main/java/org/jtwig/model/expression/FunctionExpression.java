package org.jtwig.model.expression;

import org.jtwig.model.position.Position;

import java.util.ArrayList;
import java.util.List;

public class FunctionExpression extends InjectableExpression {
    private final String functionIdentifier;
    private final List<Expression> arguments;

    public FunctionExpression(Position position, String functionIdentifier, List<Expression> arguments) {
        super(position);
        this.functionIdentifier = functionIdentifier;
        this.arguments = arguments;
    }

    public String getFunctionIdentifier() {
        return functionIdentifier;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public Expression inject(Expression expression) {
        List<Expression> arguments = new ArrayList<>();
        arguments.add(expression);
        arguments.addAll(getArguments());
        return new FunctionExpression(getPosition(), functionIdentifier, arguments);
    }
}
