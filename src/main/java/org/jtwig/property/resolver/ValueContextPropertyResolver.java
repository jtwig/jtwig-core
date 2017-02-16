package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.context.ValueContext;

public class ValueContextPropertyResolver implements PropertyResolver {
    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        if (request.getContext() == null) return Optional.absent();
        if (!(request.getContext() instanceof ValueContext)) return Optional.absent();

        return Optional.of(new Value(((ValueContext) request.getContext()).resolve(request.getPropertyName().get())));
    }
}
