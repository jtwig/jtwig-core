package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldPropertyResolver implements PropertyResolver {
    private static final Logger logger = LoggerFactory.getLogger(FieldPropertyResolver.class);
    private final JavaField field;

    public FieldPropertyResolver(JavaField field) {
        this.field = field;
    }

    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        try {
            return Optional.of(new Value(field.value(request.getContext())));
        } catch (IllegalAccessException | IllegalArgumentException e) {
            logger.debug("Unable to access field {} on object {}", field, request.getContext());
            return Optional.absent();
        }
    }
}
