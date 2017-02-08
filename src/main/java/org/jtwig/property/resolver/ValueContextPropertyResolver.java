package org.jtwig.property.resolver;

import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.value.Undefined;
import org.jtwig.value.context.ValueContext;

public class ValueContextPropertyResolver implements PropertyResolver {
    @Override
    public Object resolve(PropertyResolveRequest request) {
        if (request.getContext() == null) return Undefined.UNDEFINED;
        if (!(request.getContext() instanceof ValueContext)) return Undefined.UNDEFINED;

        return ((ValueContext) request.getContext()).resolve(request.getPropertyName().get());
    }
}
