package org.jtwig.property.configuration;

import org.jtwig.property.selection.cache.SelectionPropertyResolverCache;
import org.jtwig.property.strategy.PropertyResolverStrategy;

import java.util.List;

public class PropertyResolverConfiguration {
    private final SelectionPropertyResolverCache cache;
    private final List<PropertyResolverStrategy> propertyResolverStrategies;

    public PropertyResolverConfiguration(SelectionPropertyResolverCache cache, List<PropertyResolverStrategy> propertyResolverStrategies) {
        this.cache = cache;
        this.propertyResolverStrategies = propertyResolverStrategies;
    }

    public SelectionPropertyResolverCache getCache() {
        return cache;
    }

    public List<PropertyResolverStrategy> getPropertyResolverStrategies() {
        return propertyResolverStrategies;
    }
}
