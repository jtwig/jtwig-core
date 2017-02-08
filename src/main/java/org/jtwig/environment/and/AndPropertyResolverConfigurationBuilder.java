package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.property.configuration.PropertyResolverConfiguration;
import org.jtwig.property.configuration.PropertyResolverConfigurationBuilder;

public class AndPropertyResolverConfigurationBuilder extends PropertyResolverConfigurationBuilder<AndPropertyResolverConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder parent;

    public AndPropertyResolverConfigurationBuilder(EnvironmentConfigurationBuilder parent) {
        this.parent = parent;
    }

    public AndPropertyResolverConfigurationBuilder(PropertyResolverConfiguration prototype, EnvironmentConfigurationBuilder parent) {
        super(prototype);
        this.parent = parent;
    }

    @Override
    public EnvironmentConfigurationBuilder and() {
        return parent;
    }
}
