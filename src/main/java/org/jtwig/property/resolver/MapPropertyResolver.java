package org.jtwig.property.resolver;

import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.value.Undefined;

import java.util.Map;

public class MapPropertyResolver implements PropertyResolver {
    @Override
    public Object resolve(PropertyResolveRequest request) {
        if (request.getContext() == null) return Undefined.UNDEFINED;
        if (!(request.getContext() instanceof Map)) return Undefined.UNDEFINED;

        return ((Map) request.getContext()).get(request.getPropertyName().get());
    }
}
