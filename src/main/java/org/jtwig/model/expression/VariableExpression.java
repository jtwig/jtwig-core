package org.jtwig.model.expression;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.function.Argument;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

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
        return new FunctionExpression(getPosition(), identifier, Collections.singletonList(new Argument(Optional.<String>absent(), expression)));
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        Optional<JtwigValue> valueOptional = context.valueContext().value(identifier);
        if (valueOptional.isPresent()) {
            return valueOptional.get();
        } else {
            if (context.configuration().strictMode()) {
                throw new CalculationException(errorMessage(getPosition(), String.format("Unable to resolve variable '%s'", identifier)));
            } else {
                return JtwigValue.empty();
            }
        }
    }
}
