package org.jtwig.property.selection;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.resolver.request.PropertyResolveRequestFactory;
import org.jtwig.reflection.model.Value;

public class SelectionPropertyResolveService {
    private final PropertyResolveRequestFactory propertyResolveRequestFactory;

    public SelectionPropertyResolveService(PropertyResolveRequestFactory propertyResolveRequestFactory) {
        this.propertyResolveRequestFactory = propertyResolveRequestFactory;
    }

    public SelectionResult resolve(PropertyResolver propertyResolver, SelectionRequest request, Object leftValue) {
        Object value = propertyResolver.resolve(propertyResolveRequestFactory.create(request, leftValue));
        return new SelectionResult(propertyResolver, Optional.of(new Value(value)));
    }
}
