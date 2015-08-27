package org.jtwig.property;

import java.util.Collection;

public class PropertyResolverConfiguration {
    private final Collection<PropertyResolver> propertyResolvers;

    public PropertyResolverConfiguration(Collection<PropertyResolver> propertyResolvers) {
        this.propertyResolvers = propertyResolvers;
    }

    public Collection<PropertyResolver> getPropertyResolvers() {
        return propertyResolvers;
    }
}
