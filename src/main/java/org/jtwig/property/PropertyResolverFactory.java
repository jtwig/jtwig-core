package org.jtwig.property;

import java.util.Collection;

public class PropertyResolverFactory {
    public PropertyResolver create (Collection<PropertyResolver> propertyResolvers) {
        return new CompositePropertyResolver(propertyResolvers);
    }
}
