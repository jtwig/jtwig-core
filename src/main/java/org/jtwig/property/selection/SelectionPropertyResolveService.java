package org.jtwig.property.selection;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.resolver.request.PropertyResolveRequestFactory;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;

public class SelectionPropertyResolveService {
    private final PropertyResolveRequestFactory propertyResolveRequestFactory;

    public SelectionPropertyResolveService(PropertyResolveRequestFactory propertyResolveRequestFactory) {
        this.propertyResolveRequestFactory = propertyResolveRequestFactory;
    }

    public SelectionResult resolve(PropertyResolver propertyResolver, SelectionRequest request, Object leftValue) {
        Object value = propertyResolver.resolve(propertyResolveRequestFactory.create(request, leftValue));
        if (value == null || value == Undefined.UNDEFINED)
            return new SelectionResult(Optional.<PropertyResolver>absent(), Optional.of(new Value(Undefined.UNDEFINED)));
        return new SelectionResult(Optional.of(propertyResolver), Optional.of(new Value(value)));
    }
}
