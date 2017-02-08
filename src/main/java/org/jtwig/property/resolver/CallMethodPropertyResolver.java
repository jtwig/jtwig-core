package org.jtwig.property.resolver;

import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.value.Undefined;
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
    public Object resolve(PropertyResolveRequest request) {
        if (request.getContext() == null) return Undefined.UNDEFINED;

        try {
            return method.invoke(request.getContext(), new Object[]{argument});
        } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            logger.debug("Unable to retrieve value from method {} with argument {}", method, argument, e);
            return Undefined.UNDEFINED;
        }
    }
}
