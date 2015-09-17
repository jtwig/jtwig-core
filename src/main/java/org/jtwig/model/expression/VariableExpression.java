package org.jtwig.model.expression;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.function.Argument;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

import java.util.Collections;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

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
        return new FunctionExpression(getPosition(), identifier, Collections.singletonList(new Argument(expression)));
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        Optional<Value> valueOptional = context.valueContext().value(identifier);
        if (valueOptional.isPresent()) {
            return JtwigValueFactory.value(valueOptional.get().getValue(), context.environment().valueConfiguration());
        } else {
            if (context.environment().renderConfiguration().strictMode()) {
                throw new CalculationException(errorMessage(getPosition(), String.format("Variable '%s' undefined", identifier)));
            } else {
                return JtwigValueFactory.undefined(context.environment().valueConfiguration());
            }
        }
    }
}
