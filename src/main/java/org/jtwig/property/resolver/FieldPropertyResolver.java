package org.jtwig.property.resolver;

import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.java.JavaField;
import org.jtwig.value.Undefined;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldPropertyResolver implements PropertyResolver {
    private static final Logger logger = LoggerFactory.getLogger(FieldPropertyResolver.class);
    private final JavaField field;

    public FieldPropertyResolver(JavaField field) {
        this.field = field;
    }

    @Override
    public Object resolve(PropertyResolveRequest request) {
        try {
            return field.value(request.getContext());
        } catch (IllegalAccessException | IllegalArgumentException e) {
            logger.debug("Unable to access field {} on object {}", field, request.getContext());
            return Undefined.UNDEFINED;
        }
    }
}
