package org.jtwig.property.selection;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.reflection.model.Value;

public class SelectionResult {
    private final PropertyResolver propertyResolver;
    private final Optional<Value> resolvedValue;

    public SelectionResult(PropertyResolver propertyResolver, Optional<Value> resolvedValue) {
        this.propertyResolver = propertyResolver;
        this.resolvedValue = resolvedValue;
    }

    public PropertyResolver getPropertyResolver() {
        return propertyResolver;
    }

    public Optional<Value> getResolvedValue() {
        return resolvedValue;
    }
}
