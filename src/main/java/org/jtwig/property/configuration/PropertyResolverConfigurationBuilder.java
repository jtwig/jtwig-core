package org.jtwig.property.configuration;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.property.selection.cache.SelectionPropertyResolverCache;
import org.jtwig.property.strategy.PropertyResolverStrategy;
import org.jtwig.util.builder.ListBuilder;

public class PropertyResolverConfigurationBuilder<B extends PropertyResolverConfigurationBuilder> implements Builder<PropertyResolverConfiguration> {
    private SelectionPropertyResolverCache cache;
    private final ListBuilder<B, PropertyResolverStrategy> propertyResolverStrategies;

    public PropertyResolverConfigurationBuilder() {
        this.propertyResolverStrategies = new ListBuilder<>(self());
    }

    public PropertyResolverConfigurationBuilder(PropertyResolverConfiguration prototype) {
        this.cache = prototype.getCache();
        this.propertyResolverStrategies = new ListBuilder<>(self(), prototype.getPropertyResolverStrategies());
    }

    public B withCache(SelectionPropertyResolverCache cache) {
        this.cache = cache;
        return self();
    }

    public ListBuilder<B, PropertyResolverStrategy> propertyResolverStrategies() {
        return propertyResolverStrategies;
    }

    @Override
    public PropertyResolverConfiguration build() {
        return new PropertyResolverConfiguration(
                cache,
                propertyResolverStrategies.build()
        );
    }

    protected B self () {
        return (B) this;
    }
}
