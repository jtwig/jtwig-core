package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.property.strategy.method.ArgumentsConverter;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class MethodPropertyResolver implements PropertyResolver {
    private static final Logger logger = LoggerFactory.getLogger(MethodPropertyResolver.class);
    private final JavaMethod javaMethod;
    private final ArgumentsConverter argumentsConverter;

    public MethodPropertyResolver(JavaMethod javaMethod, ArgumentsConverter argumentsConverter) {
        this.javaMethod = javaMethod;
        this.argumentsConverter = argumentsConverter;
    }

    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        if (request.getContext() == null) return Optional.absent();

        Object[] arguments = request.getArguments().toArray();
        Optional<Object[]> convert = argumentsConverter.convert(javaMethod, arguments);
        if (convert.isPresent()) {
            try {
                return Optional.of(new Value(javaMethod.invoke(request.getContext(), convert.get())));
            } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
                logger.debug("Unable to retrieve value from method {} with arguments {}", javaMethod, request.getArguments(), e);
                return Optional.absent();
            }
        } else {
            logger.debug("Cannot convert arguments provided {} to defined method arguments types {}.", arguments, javaMethod.arguments());
            return Optional.absent();
        }
    }
}
