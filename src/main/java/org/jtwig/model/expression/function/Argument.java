package org.jtwig.model.expression.function;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.expression.Expression;

public class Argument {
    private final Optional<String> name;
    private final Expression expression;

    public Argument(Optional<String> name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public Optional<String> getName() {
        return name;
    }

    public FunctionArgument calculate (RenderContext context) {
        return new FunctionArgument(name, expression.calculate(context).asObject());
    }
}
