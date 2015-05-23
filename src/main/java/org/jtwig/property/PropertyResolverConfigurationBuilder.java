package org.jtwig.property;

import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.Collection;

public class PropertyResolverConfigurationBuilder implements Builder<PropertyResolverConfiguration> {
    private final Collection<PropertyResolver> propertyResolvers = new ArrayList<>();

    public PropertyResolverConfigurationBuilder () {}
    public PropertyResolverConfigurationBuilder (PropertyResolverConfiguration prototype) {
        propertyResolvers.addAll(prototype.getPropertyResolvers());
    }

    @Override
    public PropertyResolverConfiguration build() {
        return new PropertyResolverConfiguration(propertyResolvers);
    }
}
