package org.jtwig.property.strategy.method;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.MethodPropertyResolver;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.reflection.model.java.JavaMethod;

public class MethodPropertyResolverFactory {
    private final ArgumentsConverter argumentsConverter;

    public MethodPropertyResolverFactory(ArgumentsConverter argumentsConverter) {
        this.argumentsConverter = argumentsConverter;
    }

    public Optional<PropertyResolver> create (Optional<JavaMethod> method) {
        if (method.isPresent()) {
            PropertyResolver propertyResolver = new MethodPropertyResolver(method.get(), argumentsConverter);
            return Optional.of(propertyResolver);
        }

        return Optional.absent();
    }
}
