package org.jtwig.property.resolver.request;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.VariableExpression;

public class PropertyNameExtractor {
    public Optional<String> extract (Expression expression) {
        if (expression instanceof VariableExpression) {
            return Optional.of(((VariableExpression) expression).getIdentifier());
        }

        if (expression instanceof FunctionExpression) {
            return Optional.of(((FunctionExpression) expression).getFunctionIdentifier());
        }

        return Optional.absent();
    }
}
