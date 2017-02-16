package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class CallMethodPropertyResolver implements PropertyResolver {
    private static final Logger logger = LoggerFactory.getLogger(CallMethodPropertyResolver.class);
    private final JavaMethod method;
    private final String argument;

    public CallMethodPropertyResolver(JavaMethod method, String argument) {
        this.method = method;
        this.argument = argument;
    }

    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        if (request.getContext() == null) return Optional.absent();

        try {
            return Optional.of(new Value(method.invoke(request.getContext(), new Object[]{argument})));
        } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            logger.debug("Unable to retrieve value from method {} with argument {}", method, argument, e);
            return Optional.absent();
        }
    }
}
