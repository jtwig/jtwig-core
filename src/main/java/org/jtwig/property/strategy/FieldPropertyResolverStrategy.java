package org.jtwig.property.strategy;

import com.google.common.base.Optional;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.property.resolver.FieldPropertyResolver;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.reflection.model.java.JavaClassManager;
import org.jtwig.reflection.model.java.JavaField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldPropertyResolverStrategy implements PropertyResolverStrategy {
    private static final Logger logger = LoggerFactory.getLogger(FieldPropertyResolverStrategy.class);
    private final JavaClassManager javaClassManager;

    public FieldPropertyResolverStrategy(JavaClassManager javaClassManager) {
        this.javaClassManager = javaClassManager;
    }

    @Override
    public Optional<PropertyResolver> select(Request request) {
        if (request.getRightExpression() instanceof VariableExpression) {
            String fieldName = ((VariableExpression) request.getRightExpression()).getIdentifier();
            Optional<JavaField> field = javaClassManager.metadata(request.getLeftValue().getClass()).field(fieldName);
            if (field.isPresent()) {
                PropertyResolver propertyResolver = new FieldPropertyResolver(field.get());
                return Optional.of(propertyResolver);
            }
        }

        return Optional.absent();
    }
}
