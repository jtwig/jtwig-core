package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class FieldPropertyResolver implements PropertyResolver {
    private final Logger logger = LoggerFactory.getLogger(FieldPropertyResolver.class);
    private final boolean tryPrivateField;

    public FieldPropertyResolver(boolean tryPrivateField) {
        this.tryPrivateField = tryPrivateField;
    }

    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        if (request.getArguments().isEmpty()) {
            try {
                Field declaredField = request.getEntity().getClass().getDeclaredField(request.getPropertyName());
                return fieldValue(request, declaredField);
            } catch (NoSuchFieldException e) {
                return Optional.absent();
            }
        }
        return Optional.absent();
    }

    private Optional<Value> fieldValue(PropertyResolveRequest request, Field field) {
        try {
            if (tryPrivateField) {
                field.setAccessible(true);
            }
            return Optional.of(new Value(field.get(request.getEntity())));
        } catch (IllegalAccessException e) {
            logger.debug(ErrorMessageFormatter.errorMessage(request.getPosition(), String.format("Unable to get property '%s' value", field.getName())), e);
            return Optional.absent();
        }
    }
}
