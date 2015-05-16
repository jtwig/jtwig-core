package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.context.values.ValueContext;
import org.jtwig.reflection.model.Value;

public class ValueContextPropertyResolver implements PropertyResolver {
    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        if (request.getArguments().isEmpty()) {
            Object value = request.getEntity().getValue();
            if (value instanceof ValueContext) {
                return ((ValueContext) value).value(request.getPropertyName());
            } else {
                return Optional.absent();
            }
        } else {
            return Optional.absent();
        }
    }
}
