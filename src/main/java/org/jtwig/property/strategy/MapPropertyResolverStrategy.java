package org.jtwig.property.strategy;

import com.google.common.base.Optional;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.property.resolver.MapPropertyResolver;
import org.jtwig.property.resolver.PropertyResolver;

import java.util.Map;

public class MapPropertyResolverStrategy implements PropertyResolverStrategy {
    @Override
    public Optional<PropertyResolver> select(Request request) {
        if (request.getLeftValue() instanceof Map) {
            if (request.getRightExpression() instanceof VariableExpression) {
                PropertyResolver propertyResolver = new MapPropertyResolver();
                return Optional.of(propertyResolver);
            }
        }

        return Optional.absent();
    }
}
