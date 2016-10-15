package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;
import org.jtwig.value.context.ValueContext;

public class ValueContextPropertyResolver implements PropertyResolver {
    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        if (request.getArguments().isEmpty()) {
            if (request.getEntity() == null) return Optional.absent();

            Object value = request.getEntity().getValue();
            if (value instanceof ValueContext) {
                Object resolve = ((ValueContext) value).resolve(request.getPropertyName());
                if (resolve != Undefined.UNDEFINED) {
                    return Optional.of(new Value(resolve));
                } else {
                    return Optional.absent();
                }
            } else {
                return Optional.absent();
            }
        } else {
            return Optional.absent();
        }
    }
}
