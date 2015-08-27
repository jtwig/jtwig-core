package org.jtwig.property;

public class PropertyResolverFactory {
    public PropertyResolver create (PropertyResolverConfiguration configuration) {
        return new CompositePropertyResolver(configuration.getPropertyResolvers());
    }
}
