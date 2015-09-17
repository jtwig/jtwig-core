package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaField;
import org.jtwig.util.ErrorMessageFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldPropertyResolver implements PropertyResolver {
    private final Logger logger = LoggerFactory.getLogger(FieldPropertyResolver.class);
    private final boolean tryPrivateField;

    public FieldPropertyResolver(boolean tryPrivateField) {
        this.tryPrivateField = tryPrivateField;
    }

    @Override
    public Optional<Value> resolve(final PropertyResolveRequest request) {
        if (request.getArguments().isEmpty()) {
            Optional<JavaField> field = request.getEntity()
                    .type()
                    .field(request.getPropertyName());

            if (field.isPresent()) {
                return value(request, field.get());
            }
            return Optional.absent();
        }
        return Optional.absent();
    }

    private Optional<Value> value(PropertyResolveRequest request, JavaField field) {
        try {
            return Optional.of(new Value(field.value(request.getEntity().getValue(), tryPrivateField)));
        } catch (IllegalAccessException e) {
            logger.debug(ErrorMessageFormatter.errorMessage(request.getPosition(), String.format("Unable to get property '%s' value", field.name())), e);
            return Optional.absent();
        }
    }
}
